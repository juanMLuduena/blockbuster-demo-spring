package com.spring.controller;

import com.spring.model.Employee;
import com.spring.model.Person;
import com.spring.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Employee> getAll(@RequestParam(required = false)String firstname){
        return employeeService.getAll(firstname);
    }

    @PostMapping("/")
    public void addEmployee(@RequestBody Employee newEmployee){

        employeeService.addEmployee(newEmployee);
    }

    @GetMapping("/dni")
    public ResponseEntity<List<Employee>> getByDni(@RequestParam String dni){
        return ResponseEntity.ok(employeeService.getByDni(dni));
    }


}
