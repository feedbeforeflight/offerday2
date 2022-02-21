package com.example.offerdaysongs.dto.requests;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class FindByPeriodRequest {
    ZonedDateTime startTime;
    ZonedDateTime endTime;
}
