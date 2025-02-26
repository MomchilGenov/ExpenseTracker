package com.momchilgenov.springboot.mvcweb.exception;

public class ExpiredJwtTokenException extends Exception {

    public ExpiredJwtTokenException(String message, Throwable err) {
        super(message, err);
    }
}
