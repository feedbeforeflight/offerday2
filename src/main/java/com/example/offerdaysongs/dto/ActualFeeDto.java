package com.example.offerdaysongs.dto;

import com.example.offerdaysongs.model.Recording;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
public class ActualFeeDto {
    ZonedDateTime actualDate;
    RecordingDto recording;
    BigDecimal fee;
}
