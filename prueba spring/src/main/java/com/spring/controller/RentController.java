package com.spring.controller;

import com.spring.exceptions.BlockbusterAlreadyExistsException;
import com.spring.exceptions.BlockbusterDoesntExistsException;
import com.spring.service.ClientService;
import com.spring.service.EmployeeService;
import com.spring.service.MovieService;
import com.spring.service.RentService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<Object> addRent(@RequestParam String title, @RequestParam String dniClient, @RequestParam String dniEmployee) throws BlockbusterAlreadyExistsException, BlockbusterDoesntExistsException {
        rentService.addRent(title, dniClient, dniEmployee);
        return ResponseEntity.status(HttpStatus.CREATED).body("Carga de renta exitosa!");
    }
}
