package com.example.offerdaysongs.model;

import liquibase.pro.packaged.E;
import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Entity
public class Recording {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String title;
    String version;
    ZonedDateTime releaseTime;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", insertable = false, updatable = false)
    Singer singer;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id", insertable = false, updatable = false)
    Collection<Copyright> copyrightCollection;
}
