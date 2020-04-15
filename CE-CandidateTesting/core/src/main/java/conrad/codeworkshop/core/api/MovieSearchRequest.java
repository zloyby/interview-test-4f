package conrad.codeworkshop.core.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;
import org.jetbrains.annotations.Nullable;

import javax.validation.Valid;

@Value.Style(
        overshadowImplementation = true,
        depluralize = true,
        deepImmutablesDetection = true,
        allMandatoryParameters = true)
@JsonDeserialize(builder = ImmutableMovieSearchRequest.Builder.class)
@JsonSerialize(as = ImmutableMovieSearchRequest.class)
@Value.Immutable
public interface MovieSearchRequest {

    int from();

    int size();

    String query();

    @Nullable
    Sort sort();

}
