package com.example.ScrumWise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String string) {
        // TODO Auto-generated constructor stub
    }

    private static final long serialVersionUID = 1L;

}
