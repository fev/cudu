package org.scoutsfev.cudu.web.validacion;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Locale;

@ControllerAdvice
public class ErrorHandler {

    private MessageSource messageSource;

    @Autowired
    public ErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<Error> processValidationError(MethodArgumentNotValidException ex) {
        final Locale currentLocale =  LocaleContextHolder.getLocale();
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        List<Error> errors = Lists.transform(fieldErrors, fieldError -> {
            String localizedMessage = messageSource.getMessage(fieldError, currentLocale);
            return new Error(fieldError.getField(), localizedMessage);
        });
        return errors;
    }
}