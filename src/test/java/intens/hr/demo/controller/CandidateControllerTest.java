package intens.hr.demo.controller;

import intens.hr.demo.dto.CandidateRequestDTO;
import intens.hr.demo.dto.CandidateResponseDTO;
import intens.hr.demo.service.CandidateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CandidateController.class)
@Import({com.fasterxml.jackson.databind.ObjectMapper.class})
class CandidateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CandidateService candidateService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateCandidate() throws Exception {

        CandidateRequestDTO request = new CandidateRequestDTO("Test Test", null, null, null, Set.of("java"));

        CandidateResponseDTO response = new CandidateResponseDTO(1L, "Test Test", null, null, null, Set.of("java"));

        when(candidateService.addCandidate(any()))
                .thenReturn(response);

        mockMvc.perform(post("/api/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Test Test"))
                .andExpect(jsonPath("$.skills[0]").value("java"));
    }

    @Test
    void shouldSearchByName() throws Exception {

        CandidateResponseDTO dto = new CandidateResponseDTO(null, "Test Test", null, null, null, null);

        when(candidateService.getByName("Test Test"))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/candidates/search/by-name")
                        .param("name", "Test Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("Test Test"));
    }

    @Test
    void shouldAddSkill() throws Exception {

        CandidateResponseDTO dto = new CandidateResponseDTO(null, "null", null, null, null, Set.of("java"));

        when(candidateService.addSkillToCandidate(1L, "java"))
                .thenReturn(dto);

        mockMvc.perform(post("/api/candidates/1/skills")
                        .param("skill", "java"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.skills[0]").value("java"));
    }

    @Test
    void shouldDeleteCandidate() throws Exception {

        mockMvc.perform(delete("/api/candidates/1"))
                .andExpect(status().isOk());

        verify(candidateService).deleteCandidate(1L);
    }

    /* I'm well aware that there are many more tests that I could write,
    but this will have to suffice for now. I may get back to it once
    I've dealt with React, high chances I won't, the spec didn't specify
    that I have to do them for every method, always follow the spec :) */
}