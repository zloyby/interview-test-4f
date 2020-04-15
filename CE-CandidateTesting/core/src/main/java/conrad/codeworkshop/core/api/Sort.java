package conrad.codeworkshop.core.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Style(depluralize = true, deepImmutablesDetection = true, defaultAsDefault = true)
@JsonDeserialize(builder = ImmutableSort.Builder.class)
@JsonSerialize(as = ImmutableSort.class)
@Value.Immutable
public interface Sort {
    FieldName fieldName();

    default Sort.Order order() {
        return Order.ASC;
    }

    enum Order {
        DESC, ASC
    }
}
