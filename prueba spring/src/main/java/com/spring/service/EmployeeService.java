package com.spring.service;

import com.spring.exceptions.BlockbusterAlreadyExistsException;
import com.spring.exceptions.BlockbusterDoesntExistsException;
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

    public List<Employee> getAll(String firstname) throws BlockbusterDoesntExistsException {
        if (isNull(firstname)) {
            return employeeRepository.findAll();
        }
        List<Employee> employeeByFirstname = employeeRepository.findByFirstname(firstname);
        if(isNull(employeeByFirstname)) throw new BlockbusterDoesntExistsException("No se encontró un empleado llamado " + firstname + ".");
        return employeeByFirstname;
    }

    public Employee addEmployee(Employee newEmployee) throws  BlockbusterAlreadyExistsException {
        String newDni = newEmployee.getDni();
        if (newDni == null) throw new IllegalArgumentException("El dni no puede ser nulo.");
        if (employeeRepository.existsByDni(newDni))
            throw new BlockbusterAlreadyExistsException("No se pudo crear el empleado porque ya existe un empleado con el dni proporcionado");
        return employeeRepository.save(newEmployee);
    }

    public Employee getByDni(String dni) throws BlockbusterDoesntExistsException {
        if (dni == null) throw new IllegalArgumentException("El dni proporcionado es invalido");
        List<Employee> employees = employeeRepository.findByDni(dni);
        if (employees.isEmpty()) throw new BlockbusterDoesntExistsException("No se encontró un empleado con el dni: " + dni + ".");
        return employees.get(0);
    }

}
