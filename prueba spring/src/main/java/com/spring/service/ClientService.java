package com.spring.service;

import com.spring.exceptions.ClientAlreadyExists;
import com.spring.model.Client;
import com.spring.model.Employee;
import com.spring.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class ClientService {

    private ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    public List<Client> getAll(String firstname) {
        if (isNull(firstname)) {
            return clientRepository.findAll();
        }
        return clientRepository.findByFirstname(firstname);
    }



    public Client addClient(Client newClient) throws ClientAlreadyExists {
        String newDni = newClient.getDni();
        if(newDni == null) throw new IllegalArgumentException("el dni no puede ser nulo");
        if(clientRepository.existsByDni(newDni)) throw  new ClientAlreadyExists("Ya existe un cliente con el dni proporcionado");
        return clientRepository.save(newClient);
    }


    /*
    public Client addClient(Client newClient) throws ClientAlreadyExists {
        Client client = clientRepository.save(newClient);
        return Optional.ofNullable(client)
                .orElseThrow(() -> new ClientAlreadyExists("Ya existe un cliente con el dni proporcionado"));
    }

     */


    public Client getByDni(String dni) {
        return clientRepository.findByDni(dni).get(0);
    }

    public List<Client> getPremium() {
        return clientRepository.findByPremium(true);
    }
}
