package gfl.docguide.controllers.exception;


import gfl.docguide.exceptions.DataNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({DataIntegrityViolationException.class})
    protected ResponseEntity<Object> handleChildDeletion(RuntimeException runtimeException, WebRequest webRequest){

        String message = "Request was blocked: constraint violated, change another entity first";


        return handleExceptionInternal(runtimeException, message, HttpHeaders.EMPTY, HttpStatus.FORBIDDEN, webRequest);
    }

    @ExceptionHandler({DataNotFoundException.class})
    protected ResponseEntity<Object> handleDataNotFoundException(RuntimeException runtimeException, WebRequest webRequest){

        return handleExceptionInternal(runtimeException, runtimeException.getMessage(), HttpHeaders.EMPTY, HttpStatus.NOT_FOUND, webRequest);
    }

}
