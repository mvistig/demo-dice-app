package mvi.diceapp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class DiceRestErrorAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(DiceRestErrorAdvice.class);

    @ExceptionHandler(value = {MethodArgumentNotValidException.class, IllegalArgumentException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDto validationError(Exception ex, WebRequest request) {
        ErrorDto error = new ErrorDto();
        error.setOrigin("DiceApp Rest Service - Validation");
        error.setMessage(ex.getMessage());
        return error;
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorDto notFound(NoSuchElementException ex, WebRequest request) {
        ErrorDto error = new ErrorDto();
        error.setOrigin("DiceApp Rest Service");
        error.setMessage(ex.getMessage());
        return error;
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto generalException(Exception ex, WebRequest request) {
        LOGGER.error("When calling " + request.getContextPath() + " an exception was thrown:", ex);
        ErrorDto error = new ErrorDto();
        error.setOrigin("DiceApp Rest Service");
        error.setMessage(ex.getMessage());
        return error;
    }
}
