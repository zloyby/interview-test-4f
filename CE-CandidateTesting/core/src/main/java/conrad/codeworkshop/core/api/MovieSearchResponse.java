package conrad.codeworkshop.core.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ImmutableList;
import org.immutables.value.Value;

@Value.Style(
    overshadowImplementation = true,
    depluralize = true,
    deepImmutablesDetection = true,
    allMandatoryParameters = true)
@JsonDeserialize(builder = ImmutableMovieSearchResponse.Builder.class)
@JsonSerialize(as = ImmutableMovieSearchResponse.class)
@Value.Immutable
public interface MovieSearchResponse {

  MovieSearchRequest request();

  ImmutableList<SearchHit> hits();
}
