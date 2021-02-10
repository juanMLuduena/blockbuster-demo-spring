package com.actores.actores.microservicio.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    Integer id;
    String title;

}
