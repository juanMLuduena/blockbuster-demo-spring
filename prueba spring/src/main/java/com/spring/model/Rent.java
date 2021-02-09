package com.spring.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "rents")
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value="rent-emp")
    @JoinColumn(name = "id_employee")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value="rent-client")
    @JoinColumn(name = "id_client")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value="rent-movie")
    @JoinColumn(name = "id_movie")
    private Movie movie;


    public Rent(Employee employee, Client client, Movie movie) {
        this.employee = employee;
        this.client = client;
        this.movie = movie;
    }
}
