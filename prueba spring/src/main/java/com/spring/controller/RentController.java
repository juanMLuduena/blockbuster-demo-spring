package com.spring.controller;

import com.spring.exceptions.ClientDoesntExists;
import com.spring.exceptions.EmployeeDoesntExists;
import com.spring.exceptions.MovieAlreadyRented;
import com.spring.exceptions.WrongData;
import com.spring.model.Client;
import com.spring.model.Employee;
import com.spring.model.Movie;
import com.spring.model.Rent;
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

import java.util.List;
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
    public List<Rent> getAll() {
        return rentService.getAll();
    }

    @PostMapping("/")
    public void addRent(@RequestBody Rent newRent) throws WrongData {
        rentService.addRent(newRent);
    }

    @PostMapping("/addByIds")
    public ResponseEntity<Rent> addRent(@RequestParam Integer idMovie, @RequestParam int idClient, @RequestParam int idEmployee) throws WrongData {
        ResponseEntity response = null;
        try {
            rentService.addRent(new Rent(new Employee(idEmployee), new Client(idClient), new Movie(idMovie)));
            response = ResponseEntity.status(HttpStatus.CREATED).build();
            log.log(Level.FINE, "Entidad renta creada sin problemas");
        } catch (WrongData e) {
            log.log(Level.WARNING, "Credenciales incorrectos al agregar renta");
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } finally {
            return response;
        }
    }


    @PostMapping("/add")
    public ResponseEntity<Rent> addRent(@RequestParam String title, @RequestParam String dniClient, @RequestParam String dniEmployee) {
        ResponseEntity response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        try {
            rentService.addRent(title, dniClient, dniEmployee);
            response = ResponseEntity.status(HttpStatus.CREATED).body("Carga de renta exitosa!");
            log.log(Level.FINE, "Entidad renta creada sin problemas");
        } catch (MovieAlreadyRented me) {
            log.log(Level.WARNING, "La película que trato de alquilar ya estaba alquilada");
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La película que trato de alquilar ya estaba alquilada");
        } catch (ClientDoesntExists ce) {
            log.log(Level.WARNING, "El cliente proporcionado no se encuentra en la base de datos");
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El cliente proporcionado no se encuentra en la base de datos");
        } catch (EmployeeDoesntExists ee) {
            log.log(Level.WARNING, "El empleado proporcionado no se encuentra en la base de datos");
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El empleado proporcionado no se encuentra en la base de datos");
        } catch (WrongData e) {
            log.log(Level.WARNING, "Credenciales incorrectos al agregar renta");
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Datos incorrectos proporcionados");
        } catch(Exception e){
            log.log(Level.WARNING, "Seguramente un error en MySQL");
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error desconocido");
        }
        return response;
    }
}
