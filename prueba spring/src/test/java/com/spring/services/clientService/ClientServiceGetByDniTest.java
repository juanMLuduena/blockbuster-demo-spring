package com.spring.services.clientService;

import com.spring.exceptions.BlockbusterDoesntExistsException;
import com.spring.model.Client;
import com.spring.repository.ClientRepository;
import com.spring.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

public class ClientServiceGetByDniTest {

    @Mock
    ClientRepository clientRepository;

    @InjectMocks
    ClientService clientService;


    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    List<Client> clientList;

    //Camino feliz encuentro al cliente que busque por dni
    @Test
    void getByDniOkTest() throws BlockbusterDoesntExistsException {
        clientList = mock(List.class);
        when(clientRepository.findByDni("123")).thenReturn(clientList);
        Client clientResult = clientService.getByDni("123");
        verify(clientRepository,times(1)).findByDni("123");
        assertEquals(clientList.get(0),clientResult);
    }

    //Camino infeliz no encuentro al cliente que busque por dni
    @Test
    void getByDniThrowBlockbusterDoesntExistsExceptionTest() throws BlockbusterDoesntExistsException {
        clientList = new ArrayList<>();
        when(clientRepository.findByDni("123")).thenReturn(clientList);
        assertThrows(BlockbusterDoesntExistsException.class,()->{
            clientService.getByDni("123");
        });
    }

    //Camino infeliz el dni ingresado es null
    @Test
    void getByDniThrowIllegalArgumentExceptionTest(){
        assertThrows(IllegalArgumentException.class,()->{
            clientService.getByDni(null);
        });
    }

}
