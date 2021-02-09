package com.spring.service;

import com.spring.exceptions.EmployeeAlreadyExists;
import com.spring.exceptions.EmployeeDoesntExists;
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

    public Employee addEmployee(Employee newEmployee) throws EmployeeAlreadyExists {
        String newDni = newEmployee.getDni();
        if (newDni == null) throw new IllegalArgumentException("el dni no puede ser nulo");
        if (employeeRepository.existsByDni(newDni)) throw new EmployeeAlreadyExists("Ya existe ese cliente");
        return employeeRepository.save(newEmployee);
    }

    public List<Employee> getAll(String firstname) {
        if (isNull(firstname)) {
            return employeeRepository.findAll();
        }
        return employeeRepository.findByFirstname(firstname);
    }

    public Employee getByDni(String dni) throws EmployeeDoesntExists {
        if (dni == null) throw new IllegalArgumentException("El dni proporcionado es invalido");
        List<Employee> employees = employeeRepository.findByDni(dni);
        if (employees.isEmpty()) throw new EmployeeDoesntExists("El cliente a buscar no existe");
        return employees.get(0);
    }

    /*
    public Employee findById(Integer personId) {
        return personRepository.findById(personId);
    }

 */
}
