package ru.bazhenov.shoplist.controller.common;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

public class ValidationUtils {

    public static String getValidationErrors(BindingResult validation) {
        return validation.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .distinct()
                .collect(Collectors.toList())
                .toString();
    }
}
