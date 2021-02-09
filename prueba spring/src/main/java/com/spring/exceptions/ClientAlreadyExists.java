package com.spring.exceptions;

public class ClientAlreadyExists extends Exception{

    public ClientAlreadyExists(String message) {
        super(message);
    }
}
