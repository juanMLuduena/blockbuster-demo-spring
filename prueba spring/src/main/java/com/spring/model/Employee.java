package com.spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee extends Person {


    private Integer salary;


    public Employee(Integer id) {
        super(id);
    }

    public Employee(String firstname, String lastname, String dni) {
        super(firstname, lastname, dni);

    }

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rent> rents;
}
