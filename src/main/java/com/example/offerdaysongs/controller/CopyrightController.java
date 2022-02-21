package com.example.offerdaysongs.controller;

import com.example.offerdaysongs.dto.*;
import com.example.offerdaysongs.dto.requests.CreateCopyrightRequest;
import com.example.offerdaysongs.dto.requests.FindByPeriodRequest;
import com.example.offerdaysongs.model.Copyright;
import com.example.offerdaysongs.service.CopyrightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/copyrights")
public class CopyrightController {
    private static final String ID = "id";
    private final CopyrightService copyrightService;

    public CopyrightController(CopyrightService copyrightService) {
        this.copyrightService = copyrightService;
    }

    @GetMapping("/")
    public List<CopyrightDto> getAll() {
        return copyrightService.getAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id:[\\d]+}")
    public ResponseEntity<CopyrightDto> get(@PathVariable(ID) long id) {
        var copyright = copyrightService.getById(id);
        return new ResponseEntity<>(convertToDto(copyright), HttpStatus.OK);
    }

    @GetMapping("/bycompany/{id:[\\d]+}")
    public List<CopyrightDto> findByCompany(@PathVariable(ID) long companyId) {
        return copyrightService.findByCompany(companyId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    @GetMapping("/byperiod/")
    public List<CopyrightDto> findByPeriod(@RequestBody FindByPeriodRequest request) {
        return copyrightService.findByPeriod(request.getStartTime(), request.getEndTime()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/")
    public ResponseEntity<CopyrightDto> create(@RequestBody CreateCopyrightRequest request) {
        try {
            return new ResponseEntity<>(convertToDto(copyrightService.create(request)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id:[\\d]+}")
    public ResponseEntity<CopyrightDto> update(@PathVariable(ID) long id, @RequestBody CopyrightDto request) {
        try {
            return new ResponseEntity<>(convertToDto(copyrightService.update(id, request)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private CopyrightDto convertToDto(Copyright copyright) {
        var company = copyright.getCompany();
        var recording = copyright.getRecording();
        var singer = recording != null ? recording.getSinger() : null;
        return new CopyrightDto(copyright.getId(),
                copyright.getStartTime(),
                copyright.getEndTime(),
                company != null ? new CompanyDto(company.getId(), company.getName()) : null,
                recording != null ? new RecordingDto(
                        recording.getId(),
                        recording.getTitle(),
                        recording.getVersion(),
                        recording.getReleaseTime(),
                        singer != null ? new SingerDto(singer.getId(), singer.getName()) : null) : null);
    }
}
