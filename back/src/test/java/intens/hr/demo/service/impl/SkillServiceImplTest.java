package intens.hr.demo.service.impl;

import intens.hr.demo.model.Skill;
import intens.hr.demo.repo.SkillRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SkillServiceImplTest {

    @Mock
    private SkillRepo skillRepo;

    @InjectMocks
    private SkillServiceImpl skillService;

    @Test
    void shouldReturnExistingSkillWhenSkillExists() {
        Skill skill = new Skill();
        skill.setId(1L);
        skill.setName("skill");

        when(skillRepo.findByName("skill"))
                .thenReturn(Optional.of(skill));

        Skill result = skillService.addSkillOrReturnExisting("skill");

        assertEquals("skill", result.getName());

        verify(skillRepo).findByName("skill");
        verify(skillRepo, never()).save(any());
    }

    @Test
    void shouldReturnExistingSkillWhenSkillDoesntExist() {
        when(skillRepo.findByName("skill"))
                .thenReturn(Optional.empty());

        when(skillRepo.save(any(Skill.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Skill result = skillService.addSkillOrReturnExisting("skill");

        assertEquals("skill", result.getName());

        verify(skillRepo).findByName("skill");
        verify(skillRepo).save(any(Skill.class));
    }
}