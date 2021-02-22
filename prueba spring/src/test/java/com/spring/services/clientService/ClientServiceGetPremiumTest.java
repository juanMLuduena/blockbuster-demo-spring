package com.spring.services.clientService;

import com.spring.model.Client;
import com.spring.repository.ClientRepository;
import com.spring.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class ClientServiceGetPremiumTest {
    @Mock
    ClientRepository clientRepository;

    @InjectMocks
    ClientService clientService;

    List<Client> clientList;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    //Camino feliz devuelve un arreglo con clientes o vacio
    @Test
    void getPremiumOkTest(){
        clientList = mock(List.class);
        when(clientRepository.findByPremium(true)).thenReturn(clientList);
        List<Client> clientListResult = clientService.getPremium();
        verify(clientRepository,times(1)).findByPremium(true);
        assertEquals(clientList,clientListResult);
    }
}
