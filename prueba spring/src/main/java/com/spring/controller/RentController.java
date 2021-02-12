package com.spring.controller;

import com.spring.exceptions.*;
import com.spring.service.ClientService;
import com.spring.service.EmployeeService;
import com.spring.service.MovieService;
import com.spring.service.RentService;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;

@Log
@NoArgsConstructor
@RestController
@RequestMapping("/rent")
public class RentController {

    private RentService rentService;


    @Autowired
    public RentController(RentService rentService, ClientService clientService, EmployeeService employeeService, MovieService movieService) {
        this.rentService = rentService;
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(rentService.getAll());
    }


    @PostMapping("/")
    public ResponseEntity<Object> addRent(@RequestParam String title, @RequestParam String dniClient, @RequestParam String dniEmployee) {
        ResponseEntity response;
        try {
            rentService.addRent(title, dniClient, dniEmployee);
            response = ResponseEntity.status(HttpStatus.CREATED).body("Carga de renta exitosa!");
            log.log(Level.FINE, "Entidad renta creada sin problemas");
        } catch (BlockbusterDoesntExistsException | BlockbusterAlreadyExistsException | IllegalArgumentException e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            log.log(Level.WARNING, e.getMessage());
        }
        return response;
    }
}
