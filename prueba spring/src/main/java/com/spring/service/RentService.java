package com.spring.service;

import com.spring.exceptions.*;
import com.spring.model.Client;
import com.spring.model.Employee;
import com.spring.model.Movie;
import com.spring.model.Rent;
import com.spring.repository.RentRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Log
@Service
public class RentService {

    private final RentRepository rentRepository;
    private ClientService clientService;
    private EmployeeService employeeService;
    private MovieService movieService;

    @Autowired
    public RentService(RentRepository rentRepository, ClientService clientService, EmployeeService employeeService, MovieService movieService) {
        this.rentRepository = rentRepository;
        this.clientService = clientService;
        this.employeeService = employeeService;
        this.movieService = movieService;
    }

    public Rent addRent(String title, String dniCliente, String dniEmployee) throws BlockbusterDoesntExistsException, BlockbusterAlreadyExistsException {
        Rent rent = null;
        Movie movie = null;
        Client client = null;
        Employee employee = null;

        if (isNull(title) || isNull(dniCliente) || isNull(dniEmployee))
            throw new IllegalArgumentException("Los datos son invalidos.");

        // Los 3 métodos que cargan los objetos tiran excepciones de validación
        movie = movieService.findByTitleAndRented(title, false);
        client = clientService.getByDni(dniCliente);
        employee = employeeService.getByDni(dniEmployee);

        rent = rentRepository.save(new Rent(employee, client, movie));
        movieService.rentMovie(movie);
        return rent;
    }

    public List<Rent> getAll() {
        return rentRepository.findAll();
    }
}
