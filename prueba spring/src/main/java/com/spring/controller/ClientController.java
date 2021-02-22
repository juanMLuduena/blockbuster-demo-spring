package com.spring.controller;

import com.spring.exceptions.BlockbusterAlreadyExistsException;
import com.spring.exceptions.BlockbusterDoesntExistsException;
import com.spring.model.Client;
import com.spring.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAll(@RequestParam(required = false) String firstname) throws BlockbusterDoesntExistsException {
        return ResponseEntity.ok(clientService.getAll(firstname));
    }

    @PostMapping("/")
    public ResponseEntity<Object> addClient(@RequestBody Client newClient) throws BlockbusterAlreadyExistsException {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.addClient(newClient)) ;
    }

    @GetMapping("/{dni}")
    public ResponseEntity<Object> getByDni(@PathVariable String dni) throws BlockbusterDoesntExistsException {
        return ResponseEntity.ok(clientService.getByDni(dni));
    }

    @GetMapping("/history/{dni}")
    public ResponseEntity<Object> getMovieRentedTitleByDni(@PathVariable String dni) throws BlockbusterDoesntExistsException {
        return ResponseEntity.ok(clientService.getMovieRentedTitleByDni(dni));
    }

    @GetMapping("/premium")
    public ResponseEntity<Object> getPremiumClients() {
        return ResponseEntity.ok(clientService.getPremium());
    }
}
