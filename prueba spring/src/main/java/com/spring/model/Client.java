package com.spring.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class Client extends Person{

    private Boolean premium;

    public Client(Integer id) {
        super(id);
    }

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rent> rents;

    public Client(Integer id, String firstname, String lastname, String dni, Boolean premium) {
        super(id, firstname, lastname, dni);
        this.premium = premium;
        this.rents = new ArrayList<>();
    }
}
