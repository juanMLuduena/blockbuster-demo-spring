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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

class ClientServiceGetAllTest {

    @Mock
    ClientRepository clientRepository;

    @InjectMocks
    ClientService clientService;

    List<Client> clientList;


    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    //Camino feliz sin parámetro devolviendo clientes
    @Test
    void getAllOkTest() throws BlockbusterDoesntExistsException {
        clientList = mock(List.class);
        when(clientRepository.findAll()).thenReturn(clientList);
        List<Client> clientListResult = clientService.getAll(null);
        verify(clientRepository, times(1)).findAll();
        assertEquals(clientList, clientListResult);
    }

    //Camino feliz poniendo nombre y encontrando clientes
    @Test
    void getClientByFirstNameOkTest()throws BlockbusterDoesntExistsException{
        clientList = mock(List.class);
        when(clientRepository.findByFirstname("cacho")).thenReturn(clientList);
        List<Client> clientListResult = clientService.getAll("cacho");
        verify(clientRepository, times(1)).findByFirstname("cacho");
        assertEquals(clientList, clientListResult);
    }

    //Camino infeliz poniendo nombre pero sin encontrar clientes
    @Test
    void getClientByFirstNameThrowBlockBusterDoesntExistsExceptionTest() throws BlockbusterDoesntExistsException{
        clientList = new ArrayList<>();
        when(clientRepository.findByFirstname("cacho")).thenReturn( clientList);
        assertThrows(BlockbusterDoesntExistsException.class,() -> {
            clientService.getAll("cacho");
        });
    }

}
