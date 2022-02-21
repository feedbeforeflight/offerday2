package com.example.offerdaysongs.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id", insertable = false, updatable = false)
    Collection<Copyright> copyrightCollection;

}
