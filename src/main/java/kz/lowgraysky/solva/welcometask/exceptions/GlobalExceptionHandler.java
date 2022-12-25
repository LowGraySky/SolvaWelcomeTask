package kz.lowgraysky.solva.welcometask.exceptions;

import kz.lowgraysky.solva.welcometask.pojos.ErrorResponseEntityPojo;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException exception){
        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        return new ResponseEntity<>(new ErrorResponseEntityPojo(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EnrichmentFromRemoteException.class})
    public ResponseEntity<?> handleEnrichmentFromRemoteException(EnrichmentFromRemoteException exception){
        return new ResponseEntity<>(new ErrorResponseEntityPojo(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MissingDataException.class})
    public ResponseEntity<?> handleMissingDataException(MissingDataException exception){
        return new ResponseEntity<>(new ErrorResponseEntityPojo(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception){
        return new ResponseEntity<>(new ErrorResponseEntityPojo(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
