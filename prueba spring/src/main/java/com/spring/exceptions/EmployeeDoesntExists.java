package com.spring.exceptions;

public class EmployeeDoesntExists extends Exception{
    public EmployeeDoesntExists(String message) {
        super(message);
    }
}
