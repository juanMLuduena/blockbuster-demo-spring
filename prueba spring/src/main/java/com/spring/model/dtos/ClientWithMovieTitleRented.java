package com.spring.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientWithMovieTitleRented {

    private String firstname;
    private String lastname;
    private String dni;
    private List<String> movieTitles;
}
