package br.com.zup.Credicard.validation;

import br.com.zup.Credicard.exception.DomainException;
import br.com.zup.Credicard.exception.MultipleFieldsException;
import br.com.zup.Credicard.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ValidationHandler {
    @Autowired
    private MessageSource ms;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ValidationResponse handleNotFoundException(NotFoundException e) {
        return new ValidationResponse(
            e.getField(),
            e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(DomainException.class)
    public ValidationResponse handleDomainException(DomainException e) {
        return new ValidationResponse(
            e.getField(),
            e.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MultipleFieldsException.class)
    public List<ValidationResponse> handleMultipleException(MultipleFieldsException e) {
        List<ValidationResponse> responses = new ArrayList<>();

        e.getMessages().forEach((field, messages) -> {
            responses.add(new ValidationResponse(field, messages));
        });

        return responses;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public List<ValidationResponse> handleBindException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();

        return bindingResult.getFieldErrors().stream().map(error ->
            new ValidationResponse(error.getField(), getLocalMessage(error))
        ).collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ValidationResponse> handleMethodException(MethodArgumentNotValidException e) {
        return handleBindException(e);
    }

    public String getLocalMessage(ObjectError e) {
        return ms.getMessage(e, LocaleContextHolder.getLocale());
    }
}
