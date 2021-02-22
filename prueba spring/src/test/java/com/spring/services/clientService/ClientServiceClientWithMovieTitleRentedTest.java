package com.spring.services.clientService;

import com.spring.exceptions.BlockbusterDoesntExistsException;
import com.spring.model.Client;
import com.spring.model.dtos.ClientWithMovieTitleRented;
import com.spring.repository.ClientRepository;
import com.spring.repository.MovieRepository;
import com.spring.service.ClientService;
import com.spring.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class ClientServiceClientWithMovieTitleRentedTest {

    @Mock
    ClientRepository clientRepository;
    @Mock
    MovieRepository movieRepository;
    @Mock
    MovieService movieService;

    @InjectMocks
    ClientService clientService;



    ClientWithMovieTitleRented clientDto;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }


    //camino feliz hace el listado de peliculas del cliente
    @Test
    void clientWithMovieTitleRentedOkTest() throws BlockbusterDoesntExistsException {
        Client client = new Client(1,"Martin","Palermo","123",true);
        List<Client> clientList = new ArrayList<Client>();
        clientList.add(client);
        List<String> movieTitles = mock(List.class);
        clientDto = new ClientWithMovieTitleRented("Martin","Palermo","123",movieTitles);
        when(clientRepository.findByDni("123")).thenReturn(clientList);
        when(movieService.getMovieTitlesByClientDni("123")).thenReturn(movieTitles);
        when(movieRepository.findMovieTitlesByClientDni("123")).thenReturn(movieTitles);
        ClientWithMovieTitleRented clientDtoResult = clientService.getMovieRentedTitleByDni("123");
        verify(clientRepository,times(1)).findByDni("123");
        assertEquals(clientDto, clientDtoResult);
    }

    //camino infeliz dni nulo
    //camino infeliz no encuentra un cliente con el dni

    //TODO terminar estos 2 test

}
