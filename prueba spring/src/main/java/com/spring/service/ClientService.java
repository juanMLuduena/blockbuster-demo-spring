package com.spring.service;

import com.spring.exceptions.BlockbusterAlreadyExistsException;
import com.spring.exceptions.BlockbusterDoesntExistsException;
import com.spring.model.Client;
import com.spring.model.dtos.ClientWithMovieTitleRented;
import com.spring.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class ClientService {

    private ClientRepository clientRepository;
    private MovieService movieService;

    @Autowired
    public ClientService(ClientRepository clientRepository, MovieService movieService) {
        this.clientRepository = clientRepository;
        this.movieService = movieService;
    }


    public List<Client> getAll(String firstname) throws BlockbusterDoesntExistsException {
        if (isNull(firstname)) {
            return clientRepository.findAll();
        }
        List<Client> clientByFirstname = clientRepository.findByFirstname(firstname);
        if(clientByFirstname.isEmpty()) throw new BlockbusterDoesntExistsException("No se encontró un cliente llamado " + firstname + ".");
        return clientByFirstname;
    }

    public Client addClient(Client newClient) throws BlockbusterAlreadyExistsException {
        String newDni = newClient.getDni();
        if (newDni == null) throw new IllegalArgumentException("El dni no puede ser nulo.");
        if (clientRepository.existsByDni(newDni))
            throw new BlockbusterAlreadyExistsException("No se pudo crear el cliente porque ya existe un cliente con el dni proporcionado");
        return clientRepository.save(newClient);
    }

    public Client getByDni(String dni) throws BlockbusterDoesntExistsException {
        if (dni == null) throw new IllegalArgumentException("El dni no puede ser nulo.");
        List<Client> clients = clientRepository.findByDni(dni);
        if (clients.isEmpty()) throw new BlockbusterDoesntExistsException("No se encontró un cliente con el dni: " + dni + ".");
        return clients.get(0);
    }

    public List<Client> getPremium() {
        return clientRepository.findByPremium(true);
    }

    public ClientWithMovieTitleRented getMovieRentedTitleByDni(String dni) throws BlockbusterDoesntExistsException {
        Client client = this.getByDni(dni);
        List<String> movieTitles = movieService.getMovieTitlesByClientDni(dni);
        ClientWithMovieTitleRented clientDto = new ClientWithMovieTitleRented();
        clientDto.setFirstname(client.getFirstname());
        clientDto.setLastname(client.getLastname());
        clientDto.setDni(dni);
        clientDto.setMovieTitles(movieTitles);
        return clientDto;

    }
}
