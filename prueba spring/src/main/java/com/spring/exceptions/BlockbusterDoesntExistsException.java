package com.spring.exceptions;

public class BlockbusterDoesntExistsException extends Exception{
    public BlockbusterDoesntExistsException(String message) {
        super(message);
    }
}
