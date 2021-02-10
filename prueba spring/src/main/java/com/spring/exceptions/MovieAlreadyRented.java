package com.spring.exceptions;

public class MovieAlreadyRented extends Exception{

    public MovieAlreadyRented(String message) {
        super(message);
    }
}
