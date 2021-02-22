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
    public ResponseEntity<Object> getAll(@RequestParam(required = false) String firstname) throws BlockbusterDoesntExistsException {
        return ResponseEntity.ok(employeeService.getAll(firstname));
    }

    @PostMapping("/")
    public ResponseEntity<Object> addEmployee(@RequestBody Employee newEmployee) {
        return ResponseEntity.status(HttpStatus.CREATED).body("El empleado se creo correctamente");
    }

    @GetMapping("/dni")
    public ResponseEntity<Object> getByDni(@RequestParam String dni) throws BlockbusterDoesntExistsException {
        return ResponseEntity.ok(employeeService.getByDni(dni));
    }
}
