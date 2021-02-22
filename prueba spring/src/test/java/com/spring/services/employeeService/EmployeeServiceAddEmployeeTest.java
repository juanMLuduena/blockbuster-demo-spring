package com.spring.services.employeeService;

import com.spring.exceptions.BlockbusterAlreadyExistsException;
import com.spring.model.Employee;
import com.spring.repository.EmployeeRepository;
import com.spring.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class EmployeeServiceAddEmployeeTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeService employeeService;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    //Camino feliz se guarda el nuevo empleado
    @Test
    void addEmployeeOkTest() throws BlockbusterAlreadyExistsException {
        Employee employee = new Employee("Jose", "Jose","123");
        when(employeeRepository.save(employee)).thenReturn(employee);
        Employee employeeResult = employeeService.addEmployee(employee);
        verify(employeeRepository, times(1)).save(employee);
        assertEquals(employee, employeeResult);
    }

    //Camino infeliz el DNI es nulo tira excepcion
    @Test
    void addEmployeeNullDniThrowIllegalArgumentExceptionTest() {
        Employee employee = mock(Employee.class);
        assertThrows(IllegalArgumentException.class, () -> {
            employeeService.addEmployee(employee);
        });
    }

    //Camino infeliz ya hay un cliente con el DNI ingresado, tira excepcion
    @Test
    void addEmployeeThrowBlockbusterAlreadyExistsExceptionTest() {
        Employee employee = new Employee("Jose", "Jose","123");
        when(employeeRepository.existsByDni("123")).thenReturn(true);
        assertThrows(BlockbusterAlreadyExistsException.class, () -> {
            employeeService.addEmployee(employee);
        });
    }
}
