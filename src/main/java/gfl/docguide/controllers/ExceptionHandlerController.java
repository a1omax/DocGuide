package gfl.docguide.controllers;


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
        //String message =  runtimeException.getMessage();
        String message = "Deletion request was blocked: Delete children of this entity first";


        return handleExceptionInternal(runtimeException, message, HttpHeaders.EMPTY, HttpStatus.FORBIDDEN, webRequest);
    }

}
