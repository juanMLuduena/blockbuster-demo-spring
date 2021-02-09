package com.spring.service;

import com.spring.model.Employee;
import com.spring.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void addEmployee(Employee newEmployee) {
        employeeRepository.save(newEmployee);
    }

    public List<Employee> getAll(String firstname) {
        if (isNull(firstname)) {
            return employeeRepository.findAll();
        }
        return employeeRepository.findByFirstname(firstname);
    }

    public List<Employee> getByDni(String dni) {
        return employeeRepository.findByDni(dni);
    }

    /*
    public Employee findById(Integer personId) {
        return personRepository.findById(personId);
    }

 */
}
