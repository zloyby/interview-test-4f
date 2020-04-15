package conrad.codeworkshop.core.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Style(overshadowImplementation = true, depluralize = true, deepImmutablesDetection = true)
@JsonDeserialize(builder = ImmutableSearchHit.Builder.class)
@JsonSerialize(as = ImmutableSearchHit.class)
@Value.Immutable
public interface SearchHit {
  String id();

  String overview();

  String title();

  String originalTitle();

  String status();

  String popularity();

  String runtime();

  String voteAverage();

  String voteCount();

  String originalLanguage();

}
