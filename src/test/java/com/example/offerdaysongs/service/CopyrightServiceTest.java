package com.example.offerdaysongs.service;

import com.example.offerdaysongs.model.Company;
import com.example.offerdaysongs.model.Copyright;
import com.example.offerdaysongs.repository.CompanyRepository;
import com.example.offerdaysongs.repository.CopyrightRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.hamcrest.CoreMatchers;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CopyrightServiceTest {

    @MockBean
    private CopyrightRepository copyrightRepository;
    @MockBean
    private CompanyRepository companyRepository;

    @Autowired
    CopyrightService copyrightService;

    @Test
    void getAll_whenCalled_ShouldReturnList() {
        Copyright copyright1 = new Copyright();
        Copyright copyright2 = new Copyright();
        Copyright copyright3 = new Copyright();
        Mockito.when(copyrightRepository.findAll()).thenReturn(List.of(copyright1, copyright2, copyright3));

        List<Copyright> copyrightList = copyrightService.getAll();

        assertThat(copyrightList, hasSize(3));
        assertThat(copyrightList.get(0), notNullValue());
        assertThat(copyrightList.get(0), sameInstance(copyright1));
        assertThat(copyrightList.get(1), notNullValue());
        assertThat(copyrightList.get(1), sameInstance(copyright2));
        assertThat(copyrightList.get(2), notNullValue());
        assertThat(copyrightList.get(2), sameInstance(copyright3));
    }

    @Test
    void getById_whenCalledWithExistingId_ShouldReturnValue() {
        Copyright copyright2 = new Copyright();
        Mockito.when(copyrightRepository.getById(2L)).thenReturn(copyright2);

        Copyright copyright = copyrightService.getById(2L);
        assertThat(copyright, notNullValue());
        assertThat(copyright, sameInstance(copyright2));
    }

    @Test
    void getById_whenCalledWithNotExistingId_ShouldReturnNull() {
        Mockito.when(copyrightRepository.getById(3L)).thenReturn(null);

        Copyright copyright = copyrightService.getById(3L);
        assertThat(copyright, nullValue());
    }

    @Test
    void findByCompany_whenCalledWithExistingCompany_ShouldReturnList() {
        Copyright copyright1 = new Copyright();
        Copyright copyright2 = new Copyright();
        Copyright copyright3 = new Copyright();
        Company company = new Company();
        company.setId(2L);
        Mockito.when(copyrightRepository.findByCompany(company)).thenReturn(List.of(copyright1, copyright2, copyright3));
        Mockito.when(companyRepository.findById(2L)).thenReturn(java.util.Optional.of(company));

        List<Copyright> copyrightList = copyrightService.findByCompany(2L);

        assertThat(copyrightList, hasSize(3));
        assertThat(copyrightList.get(0), notNullValue());
        assertThat(copyrightList.get(0), sameInstance(copyright1));
        assertThat(copyrightList.get(1), notNullValue());
        assertThat(copyrightList.get(1), sameInstance(copyright2));
        assertThat(copyrightList.get(2), notNullValue());
        assertThat(copyrightList.get(2), sameInstance(copyright3));
    }

    @Test
    void findByCompany_whenCalledWithNonExistingCompany_ShouldReturnEmptyList() {
        Copyright copyright1 = new Copyright();
        Copyright copyright2 = new Copyright();
        Copyright copyright3 = new Copyright();
        Company company = new Company();
        company.setId(2L);
        Mockito.when(copyrightRepository.findByCompany(company)).thenReturn(List.of(copyright1, copyright2, copyright3));
        Mockito.when(companyRepository.findById(2L)).thenReturn(java.util.Optional.of(company));

        List<Copyright> copyrightList = copyrightService.findByCompany(3L);

        assertThat(copyrightList, hasSize(0));
    }

    @Test
    void findByPeriod_whenCalled_ShouldReturnList() {
        Copyright copyright1 = new Copyright();
        Copyright copyright2 = new Copyright();
        ZoneId zoneId = ZoneId.ofOffset("GMT", ZoneOffset.of("+05:00"));
        ZonedDateTime startDate = ZonedDateTime.of(2021, 01, 15, 0, 0, 0, 0, zoneId);
        ZonedDateTime endDate = ZonedDateTime.of(2021, 02, 21, 0, 0, 0, 0, zoneId);
        Mockito.when(copyrightRepository.findByStartTimeBeforeAndEndTimeAfter(endDate, startDate))
                .thenReturn(List.of(copyright1, copyright2));

        List<Copyright> copyrightList = copyrightService.findByPeriod(startDate, endDate);

        assertThat(copyrightList, hasSize(2));
        assertThat(copyrightList.get(0), notNullValue());
        assertThat(copyrightList.get(0), sameInstance(copyright1));
        assertThat(copyrightList.get(1), notNullValue());
        assertThat(copyrightList.get(1), sameInstance(copyright2));


        ArgumentCaptor<ZonedDateTime> startDateCaptor = ArgumentCaptor.forClass(ZonedDateTime.class);
        ArgumentCaptor<ZonedDateTime> endDateCaptor = ArgumentCaptor.forClass(ZonedDateTime.class);

        Mockito.verify(copyrightRepository, Mockito.times(1))
                .findByStartTimeBeforeAndEndTimeAfter(endDateCaptor.capture(), startDateCaptor.capture());
        assertThat(startDateCaptor.getValue(), sameInstance(startDate));
        assertThat(endDateCaptor.getValue(), sameInstance(endDate));
    }
}