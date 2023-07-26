package de.pim.spring.validation;

import jakarta.validation.Constraint;
import de.pim.spring.model.Item;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = HasIdValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HasId {

    String message() default "Id not specified";

    Class<?>[] groups() default {};

    Class<? extends Item>[] payload() default {};
}
