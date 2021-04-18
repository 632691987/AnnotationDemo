package com.example.annotationdemo.validator;

import org.hibernate.validator.HibernateValidator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;

public class BeanValidator
{
    private static Validator validator = Validation.byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory().getValidator();

    public static void validateObject(Object object, Class<?>... groups) throws ValidationException
    {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (constraintViolations.stream().findFirst().isPresent())
        {
            throw new ValidationException(constraintViolations.stream().findFirst().get().getMessage());
        }

    }
}
