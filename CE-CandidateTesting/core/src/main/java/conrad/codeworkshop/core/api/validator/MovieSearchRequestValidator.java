package conrad.codeworkshop.core.api.validator;

import conrad.codeworkshop.core.api.MovieSearchRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class MovieSearchRequestValidator implements ConstraintValidator<MovieSearchRequestValidation, MovieSearchRequest> {
    @Override
    public void initialize(MovieSearchRequestValidation movieSearchRequestValidation) {
    }

    @Override
    public boolean isValid(MovieSearchRequest req, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(req.sort())) {
            return true;
        }

        //FIXME: possible NPE
        return req.sort().fieldName().isSortable();
    }
}
