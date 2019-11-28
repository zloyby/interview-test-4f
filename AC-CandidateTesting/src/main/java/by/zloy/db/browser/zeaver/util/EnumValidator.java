package by.zloy.db.browser.zeaver.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class EnumValidator implements ConstraintValidator<EnumValid, String> {

    private List<String> valueList = null;

    @Override
    public void initialize(EnumValid constraintAnnotation) {
        valueList = new ArrayList<>();
        Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClass();

        Enum[] enumValArr = enumClass.getEnumConstants();

        for (Enum enumVal : enumValArr) {
            valueList.add(enumVal.toString().toUpperCase());
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean valid = value == null || valueList.contains(value.toUpperCase());

        if (!valid) {
            context.disableDefaultConstraintViolation();
            String messageTemplate = "Value should be one of [" + valueList + "]";
            context.buildConstraintViolationWithTemplate(messageTemplate).addConstraintViolation();
        }
        return valid;
    }
}