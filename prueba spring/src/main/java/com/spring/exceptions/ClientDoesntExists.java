package com.spring.exceptions;

public class ClientDoesntExists extends Exception{
    public ClientDoesntExists(String message) {
        super(message);
    }
}
