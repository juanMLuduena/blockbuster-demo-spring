package com.spring.services.clientService;

import com.spring.exceptions.BlockbusterDoesntExistsException;
import com.spring.model.Client;
import com.spring.repository.ClientRepository;
import com.spring.service.ClientService;
import com.spring.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

class ClientServiceGetAllTest {

    @Mock
    MovieService movieService;

    @Mock
    ClientRepository clientRepository;

    @InjectMocks
    ClientService clientService;

    List<Client> clientList;


    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    void getAllOkTest() throws BlockbusterDoesntExistsException {
        clientList = mock(List.class);
        when(clientRepository.findAll()).thenReturn(clientList);
        List<Client> clientListResult = clientService.getAll(null);
        verify(clientRepository, times(1)).findAll();
        assertEquals(clientList, clientListResult);
    }

    //Camino feliz poniendo nombre y encontrando usuarios
    //Camino infeliz poniendo nombre pero sin encontrar usuarios


}
