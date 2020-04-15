package conrad.codeworkshop.core.services;

import com.google.common.collect.ImmutableList;
import conrad.codeworkshop.core.Config;
import conrad.codeworkshop.core.api.*;
import conrad.codeworkshop.core.util.HitUtil;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
public final class SearchService {

  private final Config config;
  private final RestHighLevelClient restHighLevelClient;
  private static final String INDEX = "tmdb";

  public SearchService(final Config config) {
    this.config = config;
    restHighLevelClient = new RestHighLevelClient(
        RestClient.builder(
            new HttpHost(config.getElasticsearchHost(), config.getElasticsearchPort(), "http"))
            .setDefaultHeaders(new Header[]{
                new BasicHeader("Content-Type", "application/json")
            }));
  }

  public MovieSearchResponse search(final MovieSearchRequest movieSearchRequest) throws IOException {
    if (config.isShouldDoSearch()) {
      SearchRequest searchRequest = buildEsRequest(movieSearchRequest, Optional.empty());
      final SearchResponse esResponse = restHighLevelClient
          .search(searchRequest, RequestOptions.DEFAULT);

      return buildMovieSearchResponse(esResponse, movieSearchRequest);
    }

    return ImmutableMovieSearchResponse.of(movieSearchRequest);
  }

  public MovieSearchResponse searchByMovieLang(final MovieSearchRequest movieSearchRequest, final Lang lang) throws IOException {
    if (config.isShouldDoSearch()) {
      final SearchResponse esResponse = restHighLevelClient
          .search(buildEsRequest(movieSearchRequest, Optional.of(lang)), RequestOptions.DEFAULT);

      return buildMovieSearchResponse(esResponse, movieSearchRequest);
    }

    return ImmutableMovieSearchResponse.of(movieSearchRequest);
  }

  public SearchQualityResponse measureSearchQuality(final MovieSearchRequest movieSearchRequest) throws IOException {
    final MovieSearchResponse movieSearchResponse = search(movieSearchRequest);
    final ImmutableList<SearchHit> idealHits = getIdealSearchResultOrder(movieSearchResponse.hits());
    return ImmutableSearchQualityResponse.builder()
        .currentHits(movieSearchResponse.hits())
        .idealHits(idealHits)
        .build();
  }

  private MovieSearchResponse buildMovieSearchResponse(final SearchResponse esResponse, final MovieSearchRequest movieSearchRequest) {
    final ImmutableList.Builder<SearchHit> immutableListBuilder = ImmutableList.builder();
    esResponse.getHits().forEach(hit -> immutableListBuilder.add(ImmutableSearchHit.builder()
        .id(String.valueOf(HitUtil.get(hit.getSourceAsMap(), FieldName.ID.getFieldName(), "")))
        .overview(HitUtil.get(hit.getSourceAsMap(), FieldName.OVERVIEW.getFieldName(), ""))
        .title(HitUtil.get(hit.getSourceAsMap(), FieldName.TITLE.getFieldName(), ""))
        .originalTitle(HitUtil.get(hit.getSourceAsMap(), FieldName.ORIGINAL_TITLE.getFieldName(), ""))
        .status(HitUtil.get(hit.getSourceAsMap(), FieldName.STATUS.getFieldName(), ""))
        .popularity(String.valueOf(HitUtil.get(hit.getSourceAsMap(), FieldName.POPULARITY.getFieldName(), "")))
        .runtime(String.valueOf(HitUtil.get(hit.getSourceAsMap(), FieldName.RUNTIME.getFieldName(), "")))
        .voteAverage(String.valueOf(HitUtil.get(hit.getSourceAsMap(), FieldName.VOTE_AVERAGE.getFieldName(), "")))
        .voteCount(String.valueOf(HitUtil.get(hit.getSourceAsMap(), FieldName.VOTE_COUNT.getFieldName(), "")))
        .originalLanguage(HitUtil.get(hit.getSourceAsMap(), FieldName.ORIGINAL_LANGUAGE.getFieldName(), ""))
        .build()));

    return ImmutableMovieSearchResponse.builder()
        .request(movieSearchRequest)
        .hits(immutableListBuilder.build())
        .build();
  }


  private SearchRequest buildEsRequest(final MovieSearchRequest movieSearchRequest, final Optional<Lang> lang) {
    final SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    final MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders
        .multiMatchQuery(movieSearchRequest.query())
        .operator(Operator.AND)
        .slop(2)
        .field(FieldName.ORIGINAL_TITLE.getFieldName(), 20.0f)
        .field(FieldName.TITLE.getFieldName(), 12.5f)
        .field(FieldName.OVERVIEW.getFieldName(), 2.5f);
    final BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
        .must(multiMatchQueryBuilder);

    lang.ifPresent(lang1 -> boolQueryBuilder.filter(
            QueryBuilders.termQuery(FieldName.ORIGINAL_LANGUAGE.getFieldName(), lang1.toString().toLowerCase())));

    searchSourceBuilder.query(boolQueryBuilder);
    searchSourceBuilder.size(movieSearchRequest.size());
    searchSourceBuilder.from(movieSearchRequest.from());

    final Sort sort = movieSearchRequest.sort();
    if (sort != null) {
      FieldSortBuilder fieldSortBuilder = new FieldSortBuilder(sort.fieldName().getFieldName().toLowerCase());
      searchSourceBuilder.sort(fieldSortBuilder.order(SortOrder.fromString(sort.order().name())));
    }

    final SearchRequest esSearchRequest = new SearchRequest();
    esSearchRequest.indices(INDEX);
    esSearchRequest.source(searchSourceBuilder);

    return esSearchRequest;
  }

  private ImmutableList<SearchHit> getIdealSearchResultOrder(final ImmutableList<SearchHit> searchHits) {
    final OptionalDouble average = searchHits.stream().mapToDouble(searchHit -> Double.valueOf(searchHit.voteAverage())).average();
    final double averageAll = average.orElseThrow(ArithmeticException::new);

    final List<SearchHit> ideal = searchHits.stream()
            .sorted((sh1, sh2) -> calculateBayesianRating(sh1, sh2, averageAll)).collect(Collectors.toList());

    final ImmutableList.Builder<SearchHit> immutableListBuilder = ImmutableList.builder();
    return immutableListBuilder.addAll(ideal).build();
  }

  /**
   *  Calculate weighted rank (WR) = (v ÷ (v+m)) × R + (m ÷ (v+m)) × C
   *  where:
   *   R = average for the movie (mean) = (Rating)
   *   v = number of votes for the movie = (votes)
   *   m = minimum votes required to be listed in the top (now let it be 100)
   *   C = the mean vote across the whole report
   *  Used in IMDB and a lot of other video services.
   */
  private int calculateBayesianRating(SearchHit sh1, SearchHit sh2, double C) {
    long m = 1000L;
    return calculateWeight(sh2, C, m).compareTo(calculateWeight(sh1, C, m));
  }

  private Double calculateWeight(SearchHit hit, double C, long m) {
    double v2 = Double.valueOf(hit.voteCount());
    double R2 = Double.valueOf(hit.voteAverage());
    return (v2 / (v2 + m)) * R2 + (m / (v2 + m)) * C;
  }
}
