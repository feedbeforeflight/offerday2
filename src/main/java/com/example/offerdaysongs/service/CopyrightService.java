package com.example.offerdaysongs.service;

import com.example.offerdaysongs.dto.CompanyDto;
import com.example.offerdaysongs.dto.CopyrightDto;
import com.example.offerdaysongs.dto.RecordingDto;
import com.example.offerdaysongs.dto.requests.CreateCopyrightRequest;
import com.example.offerdaysongs.model.Copyright;
import com.example.offerdaysongs.model.Recording;
import com.example.offerdaysongs.repository.CompanyRepository;
import com.example.offerdaysongs.repository.CopyrightRepository;
import com.example.offerdaysongs.repository.RecordingRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class CopyrightService {
    private final CopyrightRepository copyrightRepository;
    private final CompanyRepository companyRepository;
    private final RecordingRepository recordingRepository;

    public CopyrightService(CopyrightRepository copyrightRepository, CompanyRepository companyRepository, RecordingRepository recordingRepository) {
        this.copyrightRepository = copyrightRepository;
        this.companyRepository = companyRepository;
        this.recordingRepository = recordingRepository;
    }

    public List<Copyright> getAll() {
        return copyrightRepository.findAll();
    }

    public Copyright getById(long id) {
        return copyrightRepository.getById(id);
    }

    public List<Copyright> findByCompany(long companyId) {
        var company = companyRepository.findById(companyId).orElse(null);
        if (company == null) {
            return new LinkedList<>();
        }

        return copyrightRepository.findByCompany(company);
    }

    public List<Copyright> findByPeriod(ZonedDateTime startTime, ZonedDateTime endTime) {
        return copyrightRepository.findByStartTimeBeforeAndEndTimeAfter(endTime, startTime);
    }

    public BigDecimal getActualFee(Recording recording, ZonedDateTime actualDate) {
        return copyrightRepository.sumFeeByDate(recording, actualDate);
    }

    @Transactional
    public Copyright create(CreateCopyrightRequest request) throws Exception {
        Copyright copyright = new Copyright();

        return createOrUpdate(
                copyright,
                request.getStartTime(),
                request.getEndTime(),
                request.getCompany(),
                request.getRecording());
    }

    @Transactional
    public Copyright update(long id, CopyrightDto request) throws Exception {
        Copyright copyright = copyrightRepository.getById(id);
        if (copyright == null) {
            copyright = new Copyright();
            copyright.setId(id);
        }

        return createOrUpdate(
                copyright,
                request.getStartTime(),
                request.getEndTime(),
                request.getCompany(),
                request.getRecording());
    }

    private Copyright createOrUpdate(
            Copyright copyright,
            ZonedDateTime startTime,
            ZonedDateTime endTime,
            CompanyDto companyDto,
            RecordingDto recordingDto) throws Exception {

        copyright.setStartTime(startTime);
        copyright.setEndTime(endTime);

        if (companyDto != null) {
            var company = companyRepository.findById(companyDto.getId()).orElseThrow(() -> new Exception("Company not found."));
            copyright.setCompany(company);
        }

        if (recordingDto != null) {
            var recording = recordingRepository.findById(recordingDto.getId()).orElseThrow(() -> new Exception("Recording not found."));
            copyright.setRecording(recording);
        }

        return copyrightRepository.save(copyright);
    }

}
