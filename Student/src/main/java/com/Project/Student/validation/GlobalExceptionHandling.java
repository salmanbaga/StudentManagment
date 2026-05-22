package com.Project.Student.validation;

import com.Project.Student.Models.ResponceModel;
import com.Project.Student.exception.DublicateExceptionRecource;
import com.Project.Student.exception.MaxLimitexception;
import com.Project.Student.exception.NotFoundException;
import io.lettuce.core.tracing.TraceContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandling {


        @ExceptionHandler(DublicateExceptionRecource.class)
        public ResponceModel handleDublicateException(DublicateExceptionRecource dr){
            return new ResponceModel(HttpStatus.CONFLICT,
                                     HttpStatus.CONFLICT.value(),
                                     dr.getMessage(),
                                   null);
        }

    @ExceptionHandler(NotFoundException.class)
    public ResponceModel handleNotfoundException(NotFoundException nt){
        return new ResponceModel(HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value(),
                nt.getMessage(),
                null);
    }

    @ExceptionHandler(MaxLimitexception.class)
    public ResponceModel handleMaxLimitexception(MaxLimitexception nt){
        return new ResponceModel(HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value(),
                nt.getMessage(),
                null);
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex){
            return ex.getMessage();
//            return new ResponceModel(HttpStatus.INTERNAL_SERVER_ERROR,
//                                     HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                                     "An unexcepted Error occured,Please try again",
//                                    null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    }

