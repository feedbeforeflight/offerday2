package com.example.offerdaysongs.controller;

import com.example.offerdaysongs.dto.ActualFeeDto;
import com.example.offerdaysongs.dto.RecordingDto;
import com.example.offerdaysongs.dto.SingerDto;
import com.example.offerdaysongs.dto.requests.CreateRecordingRequest;
import com.example.offerdaysongs.model.Recording;
import com.example.offerdaysongs.service.CopyrightService;
import com.example.offerdaysongs.service.RecordingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recordings")
public class RecordingController {
    private static final String ID = "id";
    private final RecordingService recordingService;
    private final CopyrightService copyrightService;

    public RecordingController(RecordingService recordingService, CopyrightService copyrightService) {
        this.recordingService = recordingService;
        this.copyrightService = copyrightService;
    }

    @GetMapping("/")
    public List<RecordingDto> getAll(){
        return recordingService.getAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id:[\\d]+}")
    public RecordingDto get(@PathVariable(ID) long id) {
        var recording = recordingService.getById(id);
        return convertToDto(recording);
    }

    @GetMapping("/{id:[\\d]+}/fee")
    public ActualFeeDto getActualFee(@PathVariable(ID) long id, @RequestBody ActualFeeDto request) {
        var recording = recordingService.getById(id);
        if (recording != null) {
            request.setRecording(convertToDto(recording));
            BigDecimal fee = copyrightService.getActualFee(recording, request.getActualDate());
            request.setFee(fee != null ? fee : new BigDecimal(0));
        }
        return request;
    }

    @PostMapping("/")
    public RecordingDto create(@RequestBody CreateRecordingRequest request) {
        return convertToDto(recordingService.create(request));
    }

    private RecordingDto convertToDto(Recording recording)
    {
        var singer = recording.getSinger();
        return new RecordingDto(recording.getId(),
                                recording.getTitle(),
                                recording.getVersion(),
                                recording.getReleaseTime(),
                                singer != null ? new SingerDto(singer.getId(), singer.getName()) : null);



    }
}
