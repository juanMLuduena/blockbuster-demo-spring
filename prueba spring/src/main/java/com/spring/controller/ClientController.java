package com.spring.controller;

import com.spring.exceptions.ClientAlreadyExists;
import com.spring.exceptions.ClientDoesntExists;
import com.spring.model.Client;
import com.spring.model.dtos.ClientWithMovieTitleRented;
import com.spring.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    public List<Client> getAll(@RequestParam(required = false)String firstname){
        return clientService.getAll(firstname);
    }

    @PostMapping("/")
    public ResponseEntity<Client> addClient(@RequestBody Client newClient) throws ClientAlreadyExists {
        ResponseEntity response = null;
        try {
            clientService.addClient(newClient);
            response = ResponseEntity.status(HttpStatus.CREATED).body("El cliente se creo correctamente");
        }catch(ClientAlreadyExists ce){
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El cliente que desea cargar ya fue registrado anteriormente");
        }catch(IllegalArgumentException ie){
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El dni no puede estar vacío");
        }
        return response;
    }

    @GetMapping("/{dni}")
    public ResponseEntity<Client> getByDni(@PathVariable String dni){
        ResponseEntity response = null;
        try{
            response = ResponseEntity.ok(clientService.getByDni(dni));
        }catch(ClientDoesntExists e){
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El cliente que quiere buscar no existe");
        }catch(IllegalArgumentException ie){
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El dni no puede estar vacío");
        }
        return response;
    }

    @GetMapping("/history/{dni}")
    public ResponseEntity<ClientWithMovieTitleRented> getMovieRentedTitleByDni(@PathVariable String dni){
        ResponseEntity response = null;
        try{
            response = ResponseEntity.ok(clientService.getMovieRentedTitleByDni(dni));
        }catch(ClientDoesntExists e){
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El cliente que quiere buscar no existe");
        }catch(IllegalArgumentException ie){
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El dni no puede estar vacío");
        }
        return response;
    }

    @GetMapping("/premium")
    public ResponseEntity<List<Client>> getPremiumClients(){
        return ResponseEntity.ok(clientService.getPremium());
    }
}
