package intens.hr.demo.util;

import intens.hr.demo.dto.CandidateResponseDTO;
import intens.hr.demo.model.Candidate;
import intens.hr.demo.model.Skill;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class EntityMapper {

    public CandidateResponseDTO mapToDTO(Candidate candidate) {

        Set<String> skills = candidate.getSkills()
                .stream()
                .map(Skill::getName)
                .collect(Collectors.toSet());

        return new CandidateResponseDTO(
                candidate.getId(),
                candidate.getFullName(),
                candidate.getDateOfBirth(),
                candidate.getContactNumber(),
                candidate.getEmail(),
                skills);
    }
}
