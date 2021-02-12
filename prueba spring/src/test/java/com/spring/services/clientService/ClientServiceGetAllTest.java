package com.spring.services.clientService;

import com.spring.model.Client;
import com.spring.repository.ClientRepository;
import com.spring.service.ClientService;
import com.spring.service.MovieService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientServiceGetAllTest {

    @Autowired
    ClientService clientService;
    @Autowired
    MovieService movieService;
    @Mock
    ClientRepository clientRepository;

    List<Client> clientList;


    @Before("")
    public void setUp() {
        clientService = new ClientService(clientRepository, movieService);
        Client client1 = new Client(1, "Philip J.", "Fry", "123", true);
        Client client2 = new Client(2, "Zapp", "Brannigan", "124", false);
        clientList.add(client1);
        clientList.add(client2);
    }
/*
    @Test
    public void getAllOkTest(){
        when(clientService.getAll(null)).thenReturn(clientList);
        List<Client> clientListResult = clientService.getAll(null);
        assertEquals(2, clientListResult.size());
        verify(clientRepository, times(1)).findAll();
    }
 */
}
