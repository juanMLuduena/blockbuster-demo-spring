package com.spring.controller;

import com.spring.exceptions.BlockbusterAlreadyExistsException;
import com.spring.exceptions.BlockbusterDoesntExistsException;
import com.spring.model.Employee;
import com.spring.service.EmployeeService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;

@Log
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;


    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAll(@RequestParam(required = false) String firstname) {
        ResponseEntity<Object> response;
        try {
            response = ResponseEntity.ok(employeeService.getAll(firstname));
            log.log(Level.FINE, "Se listaron los empleados de manera exitosa.");
        } catch (BlockbusterDoesntExistsException e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            log.log(Level.WARNING, e.getMessage());
        }
        return response;
    }

    @PostMapping("/")
    public ResponseEntity<Object> addEmployee(@RequestBody Employee newEmployee) {
        ResponseEntity<Object> response;
        try {
            employeeService.addEmployee(newEmployee);
            response = ResponseEntity.status(HttpStatus.CREATED).body("El empleado se creo correctamente");
        } catch (BlockbusterAlreadyExistsException | IllegalArgumentException e ) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            log.log(Level.WARNING, e.getMessage());
        }
        return response;
    }

    @GetMapping("/dni")
    public ResponseEntity<Object> getByDni(@RequestParam String dni) {
        ResponseEntity<Object> response;
        try {
            response = ResponseEntity.ok(employeeService.getByDni(dni));
        } catch (BlockbusterDoesntExistsException | IllegalArgumentException e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            log.log(Level.WARNING, e.getMessage());
        }
        return response;
    }

}
