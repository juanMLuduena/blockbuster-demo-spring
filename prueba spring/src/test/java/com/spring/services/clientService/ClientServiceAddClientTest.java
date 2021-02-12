package com.spring.services.clientService;

import com.spring.exceptions.BlockbusterAlreadyExistsException;
import com.spring.model.Client;
import com.spring.repository.ClientRepository;
import com.spring.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

public class ClientServiceAddClientTest {

    @Mock
    ClientRepository clientRepository;

    @InjectMocks
    ClientService clientService;


    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    //Camino feliz se guarda el nuevo cliente
    @Test
    void addClientOkTest() throws BlockbusterAlreadyExistsException {
        Client client = new Client(1, "Juan", "Perez", "123", true);
        when(clientRepository.save(client)).thenReturn(client);
        Client clientResult = clientService.addClient(client);
        verify(clientRepository, times(1)).save(client);
        assertEquals(client, clientResult);
    }

    //Camino infeliz el DNI es nulo tira excepcion
    @Test
    void addClientNullDniThrowIllegalArgumentExceptionTest() {
        Client client = new Client(1, "Juan", "Perez", null, true);
        assertThrows(IllegalArgumentException.class,()->{
            clientService.addClient(client);
        });
    }

    //Camino infeliz ya hay un cliente con el DNI ingresado, tira excepcion
    @Test
    void addClientThrowBlockbusterAlreadyExistsExceptionTest() {
        Client client = new Client(1, "Juan", "Perez", "123", true);
        when(clientRepository.existsByDni("123")).thenReturn(true);
        assertThrows(BlockbusterAlreadyExistsException.class, ()->{
            clientService.addClient(client);
        });
    }
}
