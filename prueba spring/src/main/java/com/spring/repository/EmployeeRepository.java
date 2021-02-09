package com.spring.repository;

import com.spring.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findByFirstname(String firstname);

    @Query(value = "SELECT * FROM employees e WHERE e.dni = ?1 limit 1",
            nativeQuery = true)
    List<Employee> findByDni(String dni);

    boolean existsByDni(String dni);
}
