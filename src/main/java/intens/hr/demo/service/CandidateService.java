package intens.hr.demo.service;

import intens.hr.demo.dto.CandidateRequestDTO;
import intens.hr.demo.dto.CandidateResponseDTO;
import intens.hr.demo.model.Candidate;

import java.util.List;

public interface CandidateService {

    CandidateResponseDTO addCandidate(CandidateRequestDTO dto);

    CandidateResponseDTO addSkillToCandidate(Long candidateId, String skillName);

    CandidateResponseDTO removeSkillFromCandidate(Long candidateId, String skillName);

    void deleteCandidate(Long candidateId);

    List<CandidateResponseDTO> getByName(String name);

    List<CandidateResponseDTO> getBySkill(String skillName);

    List<CandidateResponseDTO> getByAllSkills(List<String> skills);
}
