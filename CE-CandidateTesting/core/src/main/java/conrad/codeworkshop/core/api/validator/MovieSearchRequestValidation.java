package conrad.codeworkshop.core.api.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {MovieSearchRequestValidator.class})
@Retention(value = RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.PARAMETER})
public @interface MovieSearchRequestValidation {

    String message() default "has validation error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
