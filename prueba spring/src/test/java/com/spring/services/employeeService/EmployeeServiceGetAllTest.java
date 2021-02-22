package com.spring.services.employeeService;

import com.spring.exceptions.BlockbusterDoesntExistsException;
import com.spring.model.Employee;
import com.spring.repository.EmployeeRepository;
import com.spring.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class EmployeeServiceGetAllTest {
    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeService employeeService;

    List<Employee> employeeList;

    @BeforeEach
    public void setUp(){
        openMocks(this);
    }

    //Camino feliz sin parametros devolviendo Empleados
    @Test
    void getAllOkTest() throws BlockbusterDoesntExistsException {
        employeeList = Collections.emptyList();
        when(employeeRepository.findAll()).thenReturn(employeeList);
        List<Employee> employeeListResult = employeeService.getAll(null);
        verify(employeeRepository, times(1)).findAll();
        assertEquals(employeeList, employeeListResult);
    }

    //Camino feliz poniendo nombre y encontrando clientes
    @Test
    void getEmployeeByFirstNameOkTest()throws BlockbusterDoesntExistsException{
        employeeList = new ArrayList<>();
        employeeList.add(mock(Employee.class));
        when(employeeRepository.findByFirstname("cacho")).thenReturn(employeeList);
        List<Employee> employeeListResult = employeeService.getAll("cacho");
        verify(employeeRepository, times(1)).findByFirstname("cacho");
        assertEquals(employeeList, employeeListResult);
    }

    //Camino infeliz poniendo nombre pero sin encontrar clientes
    @Test
    void getEmployeeByFirstNameThrowBlockBusterDoesntExistsExceptionTest() throws BlockbusterDoesntExistsException{
        employeeList = Collections.emptyList();
        when(employeeRepository.findByFirstname("cacho")).thenReturn( employeeList);
        assertThrows(BlockbusterDoesntExistsException.class,() -> {
            employeeService.getAll("cacho");
        });
    }
}
