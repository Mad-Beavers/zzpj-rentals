package com.rentalhub.validators;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class ServiceRequestValidator {

    private static Validator validator;

    @PostConstruct
    public void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public static <T> void validate(T t) {
        Set<ConstraintViolation<T>> errors = validator.validate(t);
        if (errors.isEmpty()) {
            throw new ConstraintViolationException(errors);
        }
    }
}
