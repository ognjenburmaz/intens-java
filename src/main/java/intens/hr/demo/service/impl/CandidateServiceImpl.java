package intens.hr.demo.service.impl;

import intens.hr.demo.model.Candidate;
import intens.hr.demo.model.Skill;
import intens.hr.demo.repo.CandidateRepo;
import intens.hr.demo.repo.SkillRepo;
import intens.hr.demo.service.CandidateService;
import intens.hr.demo.service.SkillService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepo candidateRepo;
    private final SkillRepo skillRepo;
    private final SkillService skillService;

    public CandidateServiceImpl(CandidateRepo candidateRepo, SkillRepo skillRepo, SkillService skillService) {
        this.candidateRepo = candidateRepo;
        this.skillRepo = skillRepo;
        this.skillService = skillService;
    }

    @Transactional
    public Candidate addCandidate(Candidate candidate) {

        Set<Skill> managedSkills = skillService.getSkillsToSet(candidate.getSkills());

        candidate.setSkills(managedSkills);

        return candidateRepo.save(candidate);
    }


    @Transactional
    public Candidate addSkillToCandidate(Long candidateId, String skillName) {

        Candidate candidate = candidateRepo.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        Skill skill = skillRepo.findByName(skillName)
                .orElseGet(() -> {
                    Skill newSkill = new Skill();
                    newSkill.setName(skillName);
                    return skillRepo.save(newSkill);
                });

        candidate.getSkills().add(skill);

        return candidateRepo.save(candidate);
    }

    @Transactional
    public Candidate removeSkillFromCandidate(Long candidateId, String skillName) {

        Candidate candidate = candidateRepo.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        Skill skill = skillRepo.findByName(skillName)
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        candidate.getSkills().remove(skill);

        return candidateRepo.save(candidate);
    }

    public void deleteCandidate(Long candidateId) {
        candidateRepo.deleteById(candidateId);
    }

    public List<Candidate> searchByName(String name) {
        return candidateRepo.findByFullNameContainingIgnoreCase(name);
    }

    public List<Candidate> getBySkill(String skillName) {
        return candidateRepo.findBySkill(skillName);
    }

    public List<Candidate> getByAllSkills(List<String> skills) {
        return candidateRepo.findByAllSkills(skills, skills.size());
    }
}
