package de.pim.spring.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import de.pim.spring.model.Item;

public class HasIdValidator implements ConstraintValidator<HasId, Object> {

    @Override
    public void initialize(HasId constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        Long id = null;
        if (value instanceof Item) {
            id = ((Item) value).getId();
        }

        return id != null && id > 0;
    }
}
