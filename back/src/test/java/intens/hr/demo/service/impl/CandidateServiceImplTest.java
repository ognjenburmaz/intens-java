package intens.hr.demo.service.impl;

import intens.hr.demo.dto.CandidateRequestDTO;
import intens.hr.demo.dto.CandidateResponseDTO;
import intens.hr.demo.model.Candidate;
import intens.hr.demo.model.Skill;
import intens.hr.demo.repo.SkillRepo;
import intens.hr.demo.repo.CandidateRepo;
import intens.hr.demo.service.SkillService;
import intens.hr.demo.util.EntityMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CandidateServiceImplTest {

    @Mock
    private CandidateRepo candidateRepo;

    @Mock
    private SkillRepo skillRepo;

    @Mock
    private SkillService skillService;

    @Mock
    private EntityMapper entityMapper;

    @InjectMocks
    private CandidateServiceImpl candidateService;

    @Test
    void shouldCreateCandidateWithExistingSkill() {

        CandidateRequestDTO dto = new CandidateRequestDTO("Test Test", null, null, null, Set.of("java"));

        Skill existingSkill = new Skill();
        existingSkill.setId(1L);
        existingSkill.setName("java");

        when(skillService.getSkillsToSet(dto.skills()))
                .thenReturn(Set.of(existingSkill));

        Candidate savedCandidate = new Candidate();
        savedCandidate.setId(1L);
        savedCandidate.setSkills(Set.of(existingSkill));
        savedCandidate.setFullName("Test Test");

        when(candidateRepo.save(any())).thenReturn(savedCandidate);

        CandidateResponseDTO mappedDto = new CandidateResponseDTO(
                1L, "Test Test", null, null, null, Set.of("java")
        );
        when(entityMapper.mapToDTO(savedCandidate)).thenReturn(mappedDto);

        CandidateResponseDTO result = candidateService.addCandidate(dto);

        assertNotNull(result);
        assertEquals("Test Test", result.fullName());
        assertTrue(result.skills().contains("java"));

        verify(candidateRepo).save(any());
        verify(skillService).getSkillsToSet(dto.skills());
    }

    @Test
    void shouldCreateCandidateWithNewSkill() {

        CandidateRequestDTO dto = new CandidateRequestDTO("Test Test", null, null, null, Set.of("java"));

        Skill newSkill = new Skill();
        newSkill.setName("java");

        when(skillService.getSkillsToSet(dto.skills()))
                .thenReturn(Set.of(newSkill));

        Candidate savedCandidate = new Candidate();
        savedCandidate.setFullName(dto.fullName());
        savedCandidate.setSkills(new HashSet<>());

        when(candidateRepo.save(any(Candidate.class)))
                .thenReturn(savedCandidate);

        CandidateResponseDTO mapDto = new CandidateResponseDTO(1L, "Test Test", null, null, null, Set.of("java"));

        when(entityMapper.mapToDTO(any(Candidate.class))).thenReturn(mapDto);

        CandidateResponseDTO result = candidateService.addCandidate(dto);

        assertTrue(result.skills().contains("java"));

        verify(skillService).getSkillsToSet(dto.skills());
    }

    @Test
    void shouldAddSkillToCandidate() {

        Candidate candidate = new Candidate();
        candidate.setId(1L);
        candidate.setSkills(new HashSet<>());

        Skill skill = new Skill();
        skill.setName("java");

        when(candidateRepo.findById(1L))
                .thenReturn(Optional.of(candidate));

        when(skillRepo.findByName("java"))
                .thenReturn(Optional.of(skill));

        when(candidateRepo.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        CandidateResponseDTO dto = new CandidateResponseDTO(1L, "Test Test", null, null, null, Set.of("java"));

        when(entityMapper.mapToDTO(any(Candidate.class))).thenReturn(dto);

        CandidateResponseDTO result =
                candidateService.addSkillToCandidate(1L, "java");

        assertTrue(result.skills().contains("java"));
    }

    @Test
    void shouldGetByName() {

        Candidate candidate = new Candidate();
        candidate.setFullName("Test Test");
        candidate.setSkills(new HashSet<>());

        CandidateResponseDTO dto = new CandidateResponseDTO(null, "Test Test", null, null, null, null);

        when(candidateRepo.findByFullNameContainingIgnoreCase("test"))
                .thenReturn(List.of(candidate));

        when(entityMapper.mapToDTO(candidate))
                .thenReturn(dto);

        List<CandidateResponseDTO> result =
                candidateService.getByName("test");

        assertEquals(1, result.size());
        assertEquals("Test Test", result.getFirst().fullName());

        verify(candidateRepo).findByFullNameContainingIgnoreCase("test");
        verify(entityMapper).mapToDTO(candidate);
    }

    @Test
    void shouldGetBySkill() {

        Candidate candidate = new Candidate();

        CandidateResponseDTO dto = new CandidateResponseDTO(null, "Test Test", null, null, null, Set.of("java"));

        when(candidateRepo.findBySkill("java"))
                .thenReturn(List.of(candidate));

        when(entityMapper.mapToDTO(candidate))
                .thenReturn(dto);

        List<CandidateResponseDTO> result = candidateService.getBySkill("java");

        assertEquals(1, result.size());
        assertEquals("Test Test", result.getFirst().fullName());
        assertTrue(result.getFirst().skills().contains("java"));

        verify(candidateRepo).findBySkill("java");
        verify(entityMapper).mapToDTO(candidate);
    }

    @Test
    void shouldGetBySkillRepoEmpty() {
        when(candidateRepo.findBySkill("java"))
                .thenReturn(List.of());

        List<CandidateResponseDTO> result =
                candidateService.getBySkill("java");

        assertTrue(result.isEmpty());

        verify(candidateRepo).findBySkill("java");
        verify(entityMapper, never()).mapToDTO(any());
    }

    @Test
    void shouldGetBySkillMapperNull() {

        Candidate candidate = new Candidate();

        when(candidateRepo.findBySkill("java"))
                .thenReturn(List.of(candidate));

        when(entityMapper.mapToDTO(candidate))
                .thenReturn(null);

        List<CandidateResponseDTO> result =
                candidateService.getBySkill("java");

        assertNull(result.getFirst());

        verify(candidateRepo).findBySkill("java");
        verify(entityMapper).mapToDTO(any());
    }
}