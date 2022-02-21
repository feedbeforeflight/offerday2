package com.example.offerdaysongs.controller;

import com.example.offerdaysongs.dto.CopyrightDto;
import com.example.offerdaysongs.model.Copyright;
import com.example.offerdaysongs.repository.CompanyRepository;
import com.example.offerdaysongs.repository.CopyrightRepository;
import com.example.offerdaysongs.repository.RecordingRepository;
import com.example.offerdaysongs.service.CopyrightService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CopyrightController.class)
class CopyrightControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CopyrightService copyrightService;

    @SneakyThrows
    @Test
    void getAll_whenCalled_shouldReturn() {
        Copyright copyright1 = new Copyright();
        copyright1.setId(2345L);
        Copyright copyright2 = new Copyright();
        copyright2.setId(2L);
        Copyright copyright3 = new Copyright();
        copyright3.setId(3L);
        Mockito.when(copyrightService.getAll()).thenReturn(List.of(copyright1, copyright2, copyright3));

        mockMvc.perform(get("/api/copyrights/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.[:1].id").value(2345));
    }
}