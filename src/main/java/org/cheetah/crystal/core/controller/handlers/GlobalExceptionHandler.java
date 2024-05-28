package org.cheetah.crystal.core.controller.handlers;
import org.cheetah.crystal.core.exceptions.AbstractCrystalException;
import org.cheetah.crystal.rest.responses.AbstractCrystalResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AbstractCrystalException.class)
    public ResponseEntity<AbstractCrystalResponse> handleAbstractCrystalException(AbstractCrystalException ex) {
        AbstractCrystalResponse response = new AbstractCrystalResponse() {};
        response.setException(ex);
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorResponse().getStatus()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AbstractCrystalResponse> handleGenericException(Exception ex) {
        AbstractCrystalResponse response = new AbstractCrystalResponse() {};
        response.setException(new AbstractCrystalException(ex.getMessage(), "INTERNAL_SERVER_ERROR", 500) {});
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}