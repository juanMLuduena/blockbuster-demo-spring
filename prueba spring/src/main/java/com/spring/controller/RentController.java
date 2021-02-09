package com.spring.controller;

import com.spring.exceptions.WrongData;
import com.spring.model.Client;
import com.spring.model.Employee;
import com.spring.model.Movie;
import com.spring.model.Rent;
import com.spring.service.ClientService;
import com.spring.service.EmployeeService;
import com.spring.service.MovieService;
import com.spring.service.RentService;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.w3c.dom.stylesheets.LinkStyle;

import java.net.URI;
import java.util.List;
import java.util.logging.Level;

@Log
@RestController
@RequestMapping("/rent")
public class RentController {

    private final RentService rentService;

    @Autowired
    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @GetMapping("/")
    public List<Rent> getAll() {
        return rentService.getAll();
    }

    @PostMapping("/")
    public void addRent(@RequestBody Rent newRent) throws WrongData {
        rentService.addRent(newRent);
    }

    @PostMapping("/add")
    public ResponseEntity<Rent> addRent(@RequestParam Integer idMovie, @RequestParam int idClient, @RequestParam int idEmployee) throws WrongData {
        ResponseEntity response = null;
        try {
            rentService.addRent(new Rent(new Employee(idEmployee), new Client(idClient), new Movie(idMovie)));
            response = ResponseEntity.status(HttpStatus.CREATED).build();
            log.log(Level.WARNING, "Entidad renta creada sin problemas");
        } catch (WrongData e) {
            log.log(Level.WARNING, "Credenciales incorrectos al agregar renta");
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return response;
    }

    /*
     @PostMapping("/add")
    public ResponseEntity<Rent> addRent(@RequestParam String title, @RequestParam String dniClient, @RequestParam String dniEmployee){
         ResponseEntity response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
         try {
             rentService.addRent(title, dniClient, dniEmployee);
             response = ResponseEntity.status(HttpStatus.CREATED).build();
         } catch (WrongData e) {
             e.getMessage();
         }
         return response;
     }

     */
}
