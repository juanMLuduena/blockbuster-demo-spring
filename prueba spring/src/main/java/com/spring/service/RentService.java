package com.spring.service;

import com.spring.exceptions.WrongData;
import com.spring.model.Movie;
import com.spring.model.Rent;
import com.spring.repository.RentRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Log
@Service
public class RentService {

    private final RentRepository rentRepository;

    @Autowired
    public RentService(RentRepository rentRepository) {
        this.rentRepository = rentRepository;
    }

    public Rent addRent(Rent newRent) throws WrongData {
        Rent rent = null;
        try {
            rent = rentRepository.save(newRent);
        }catch(Exception e){
            e.getMessage();
        }
        return Optional.ofNullable(rent)
                .orElseThrow(() -> new WrongData("Couldn't create a rent, please check the data provided"));
    }

    /*
    public Rent addRent(String title, String dniCliente, String dniEmployee) throws WrongData {
        Rent rent = null;
        try {
            Movie movie =
            rent = rentRepository.save(newRent);
        }catch(Exception e){
            e.getMessage();
        }
        return Optional.ofNullable(rent)
                .orElseThrow(() -> new WrongData("Couldn't create a rent, please check the data provided"));
    }

     */
    public List<Rent> getAll() {
        return rentRepository.findAll();
    }
}
