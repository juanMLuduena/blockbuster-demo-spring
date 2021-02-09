package com.spring.repository;

import com.spring.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    List<Client> findByFirstname(String firstname);

    @Query(value = "SELECT * FROM clients c WHERE c.dni = ?",
            nativeQuery = true)
    List<Client> findByDni(String dni);

    @Query(value = "SELECT * FROM clients c WHERE c.premium = 1",
            nativeQuery = true)
    List<Client> findByPremium(boolean b);

    boolean existsByDni(String newDni);
}
