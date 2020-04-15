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
@JsonDeserialize(builder = ImmutableSearchQualityResponse.Builder.class)
@JsonSerialize(as = ImmutableSearchQualityResponse.class)
@Value.Immutable

public interface SearchQualityResponse {

  ImmutableList<SearchHit> currentHits();

  ImmutableList<SearchHit> idealHits();

}
