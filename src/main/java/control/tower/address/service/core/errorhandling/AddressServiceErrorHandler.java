package control.tower.address.service.core.errorhandling;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class AddressServiceErrorHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException exception, WebRequest webRequest) {
        BindingResult bindingResult = exception.getBindingResult();
        String errorMessage = bindingResult.getFieldErrors().get(0).getDefaultMessage();
        ErrorMessage errorResponse = new ErrorMessage(new Date(), errorMessage);
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleOtherExceptions(Exception exception, WebRequest webRequest) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), exception.getLocalizedMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
