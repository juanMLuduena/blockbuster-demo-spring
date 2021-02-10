package com.spring.controller;

import com.spring.exceptions.EmployeeAlreadyExists;
import com.spring.exceptions.EmployeeDoesntExists;
import com.spring.model.Employee;
import com.spring.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;


    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public List<Employee> getAll(@RequestParam(required = false) String firstname) {
        return employeeService.getAll(firstname);
    }

    @PostMapping("/")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee newEmployee) {
        ResponseEntity response = null;
        try {
            employeeService.addEmployee(newEmployee);
            response = ResponseEntity.status(HttpStatus.CREATED).body("El empleado se creo correctamente");
        } catch (EmployeeAlreadyExists e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El empleado que desea cargar ya fue registrado anteriormente");
        } catch (IllegalArgumentException ie) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El dni no puede estar vacío");
        }
        return response;
    }

    @GetMapping("/dni")
    public ResponseEntity<List<Employee>> getByDni(@RequestParam String dni) {
        ResponseEntity response = null;
        try {
            response = ResponseEntity.ok(employeeService.getByDni(dni));
        } catch (EmployeeDoesntExists e){
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El employee que quiere buscar no existe");
        } catch (IllegalArgumentException ie) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El dni no puede estar vacío");
        }
        return response;
    }

}
