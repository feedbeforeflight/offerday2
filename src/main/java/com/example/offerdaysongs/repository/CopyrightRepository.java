package com.example.offerdaysongs.repository;

import com.example.offerdaysongs.model.Company;
import com.example.offerdaysongs.model.Copyright;
import com.example.offerdaysongs.model.Recording;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

public interface CopyrightRepository  extends JpaRepository<Copyright, Long>, JpaSpecificationExecutor<Copyright> {

    public List<Copyright> findByCompany(Company company);

    public List<Copyright> findByStartTimeBeforeAndEndTimeAfter(ZonedDateTime endTime, ZonedDateTime startTime);

    @Query("select sum(c.fee) from Copyright c where c.startTime <= :actualDate and c.endTime >= :actualDate and c.recording = :recording")
    public BigDecimal sumFeeByDate(Recording recording, ZonedDateTime actualDate);

}
