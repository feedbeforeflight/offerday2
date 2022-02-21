package com.example.offerdaysongs.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@Entity
public class Copyright {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    ZonedDateTime startTime;
    ZonedDateTime endTime;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", insertable = true, updatable = true)
    Company company;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "recording_id", insertable = true, updatable = true)
    Recording recording;
    BigDecimal fee;
}
