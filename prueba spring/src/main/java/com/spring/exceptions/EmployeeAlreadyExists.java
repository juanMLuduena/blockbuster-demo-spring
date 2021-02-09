package com.spring.exceptions;

public class EmployeeAlreadyExists extends Exception{

    public EmployeeAlreadyExists(String message) {
        super(message);
    }
}
