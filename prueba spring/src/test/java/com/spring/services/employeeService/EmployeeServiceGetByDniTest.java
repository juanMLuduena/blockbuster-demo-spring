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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
public class EmployeeServiceGetByDniTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeService employeeService;

    List<Employee> employeeList;

    @BeforeEach
    public void setUp(){
        openMocks(this);
    }


    //Camino feliz encuentro al empleado que busque por dni
    @Test
    void getByDniOkTest() throws BlockbusterDoesntExistsException {
        employeeList = new ArrayList<>();
        employeeList.add(mock(Employee.class));
        when(employeeRepository.findByDni("123")).thenReturn(employeeList);
        Employee employeeResult = employeeService.getByDni("123");
        verify(employeeRepository,times(1)).findByDni("123");
        assertEquals(employeeList.get(0),employeeResult);
    }

    //Camino infeliz no encuentro al cliente que busque por dni
    @Test
    void getByDniThrowBlockbusterDoesntExistsExceptionTest() throws BlockbusterDoesntExistsException {
        employeeList = new ArrayList<>();
        when(employeeRepository.findByDni("123")).thenReturn(employeeList);
        assertThrows(BlockbusterDoesntExistsException.class,()->{
            employeeService.getByDni("123");
        });
    }

    //Camino infeliz el dni ingresado es null
    @Test
    void getByDniThrowIllegalArgumentExceptionTest(){
        assertThrows(IllegalArgumentException.class,()->{
            employeeService.getByDni(null);
        });
    }
}
