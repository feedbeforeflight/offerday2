package com.example.offerdaysongs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class CopyrightDto {
    long id;
    ZonedDateTime startTime;
    ZonedDateTime endTime;
    CompanyDto company;
    RecordingDto recording;
}
