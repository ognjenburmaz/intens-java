package intens.hr.demo.service;

import intens.hr.demo.model.Candidate;

import java.util.List;

public interface CandidateService {

    Candidate addCandidate(Candidate candidate);

    Candidate addSkillToCandidate(Long candidateId, String skillName);

    Candidate removeSkillFromCandidate(Long candidateId, String skillName);

    void deleteCandidate(Long candidateId);

    List<Candidate> searchByName(String name);

    List<Candidate> getBySkill(String skillName);

    List<Candidate> getByAllSkills(List<String> skills);
}
