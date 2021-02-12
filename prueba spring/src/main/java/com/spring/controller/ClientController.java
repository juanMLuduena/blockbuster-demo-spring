package com.spring.controller;

import com.spring.exceptions.BlockbusterAlreadyExistsException;
import com.spring.exceptions.BlockbusterDoesntExistsException;
import com.spring.model.Client;
import com.spring.service.ClientService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;

@Log
@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAll(@RequestParam(required = false) String firstname) {
        ResponseEntity<Object> response = null;
        try {
            response = ResponseEntity.ok(clientService.getAll(firstname));
            log.log(Level.FINE, "Se listaron los clientes de manera exitosa.");
        } catch (BlockbusterDoesntExistsException e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            log.log(Level.WARNING, e.getMessage());
        }
        return response;
    }

    @PostMapping("/")
    public ResponseEntity<Object> addClient(@RequestBody Client newClient) {
        ResponseEntity<Object> response = null;
        try {
            clientService.addClient(newClient);
            response = ResponseEntity.status(HttpStatus.CREATED).body("El cliente se creo correctamente.");
        } catch (BlockbusterAlreadyExistsException | IllegalArgumentException e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            log.log(Level.WARNING, e.getMessage());
        }
        return response;
    }

    @GetMapping("/{dni}")
    public ResponseEntity<Object> getByDni(@PathVariable String dni) {
        ResponseEntity<Object> response = null;
        try {
            response = ResponseEntity.ok(clientService.getByDni(dni));
        } catch (BlockbusterDoesntExistsException | IllegalArgumentException e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            log.log(Level.WARNING, e.getMessage());
        }
        return response;
    }

    @GetMapping("/history/{dni}")
    public ResponseEntity<Object> getMovieRentedTitleByDni(@PathVariable String dni) {
        ResponseEntity<Object> response = null;
        try {
            response = ResponseEntity.ok(clientService.getMovieRentedTitleByDni(dni));
        } catch (BlockbusterDoesntExistsException | IllegalArgumentException e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            log.log(Level.WARNING, e.getMessage());
        }
        return response;
    }

    @GetMapping("/premium")
    public ResponseEntity<Object> getPremiumClients() {
        return ResponseEntity.ok(clientService.getPremium());
    }
}
