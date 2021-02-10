package com.spring.service;

import com.spring.exceptions.ClientDoesntExists;
import com.spring.exceptions.EmployeeDoesntExists;
import com.spring.exceptions.MovieAlreadyRented;
import com.spring.exceptions.WrongData;
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

    public Rent addRent(Rent newRent) throws WrongData {
        Rent rent = null;
        try {
            rent = rentRepository.save(newRent);
        } catch (Exception e) {
            e.getMessage();
        }
        return Optional.ofNullable(rent)
                .orElseThrow(() -> new WrongData("Couldn't create a rent, please check the data provided"));
    }

    public Rent addRent(String title, String dniCliente, String dniEmployee) throws WrongData, ClientDoesntExists, EmployeeDoesntExists, MovieAlreadyRented {
        Rent rent = null;
        Movie movie = null;
        Client client = null;
        Employee employee = null;

        movie = movieService.findByTitleAndRented(title, false);
        //Los métodos getByDni tiran excepción
        client = clientService.getByDni(dniCliente);
        employee = employeeService.getByDni(dniEmployee);
        if (movie == null) throw new MovieAlreadyRented("La película no esta disponible");
        if ((employee == null) || (client == null)) throw new WrongData("Hubo un error interno");
        try{
            rent = rentRepository.save(new Rent(employee,client,movie));
            movieService.rentMovie(movie);
        }catch(Exception e){
            e.getMessage();
        }
        return rent;
    }

    public List<Rent> getAll() {
        return rentRepository.findAll();
    }
}
