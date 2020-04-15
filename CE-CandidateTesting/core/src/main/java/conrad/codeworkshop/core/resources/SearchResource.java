package conrad.codeworkshop.core.resources;

import com.codahale.metrics.annotation.Timed;
import conrad.codeworkshop.core.api.Lang;
import conrad.codeworkshop.core.api.MovieSearchRequest;
import conrad.codeworkshop.core.api.MovieSearchResponse;
import conrad.codeworkshop.core.api.SearchQualityResponse;
import conrad.codeworkshop.core.api.validator.MovieSearchRequestValidation;
import conrad.codeworkshop.core.services.SearchService;

import javax.inject.Singleton;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Singleton
public final class SearchResource {

    private final SearchService searchService;

    public SearchResource(final SearchService searchService) {
        this.searchService = searchService;
    }

    @POST
    @Timed
    @Path("search/")
    @Consumes(MediaType.APPLICATION_JSON)
    public MovieSearchResponse search(
            @Valid @MovieSearchRequestValidation final MovieSearchRequest movieSearchRequest) throws IOException {
        return searchService.search(movieSearchRequest);
    }

    @POST
    @Timed
    @Path("search/{lang}")
    @Consumes(MediaType.APPLICATION_JSON)
    public MovieSearchResponse searchByLang(
            @PathParam("lang") final Lang lang,
            @Valid @MovieSearchRequestValidation final MovieSearchRequest movieSearchRequest) throws IOException {
        return searchService.searchByMovieLang(movieSearchRequest, lang);
    }

    @POST
    @Timed
    @Path("measureSearchQuality/")
    @Consumes(MediaType.APPLICATION_JSON)
    public SearchQualityResponse measureSearchQuality(
            @Valid @MovieSearchRequestValidation final MovieSearchRequest movieSearchRequest) throws IOException {
        return searchService.measureSearchQuality(movieSearchRequest);
    }
}
