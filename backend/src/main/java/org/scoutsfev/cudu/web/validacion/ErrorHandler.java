package org.scoutsfev.cudu.web.validacion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
public class ErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    private MessageSource messageSource;

    @Autowired
    public ErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<Error> processValidationError(MethodArgumentNotValidException ex) {
        if (logger.isDebugEnabled())
            logger.debug("Error de validación.", ex);

        final Locale currentLocale =  LocaleContextHolder.getLocale();
        BindingResult bindingResult = ex.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        ArrayList<Error> errors = new ArrayList<>();
        for (ObjectError objectError : allErrors) {
            String target;
            if (objectError instanceof FieldError) {
                target = ((FieldError)objectError).getField();
            } else {
                target = objectError.getObjectName();
            }
            String localizedMessage = messageSource.getMessage(objectError, currentLocale);
            errors.add(new Error(target, localizedMessage));
        }
        return errors;
    }
}