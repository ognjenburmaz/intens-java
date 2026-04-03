package intens.hr.demo.service.impl;

import intens.hr.demo.dto.CandidateRequestDTO;
import intens.hr.demo.dto.CandidateResponseDTO;
import intens.hr.demo.model.Candidate;
import intens.hr.demo.model.Skill;
import intens.hr.demo.repo.CandidateRepo;
import intens.hr.demo.repo.SkillRepo;
import intens.hr.demo.service.CandidateService;
import intens.hr.demo.service.SkillService;
import intens.hr.demo.util.EntityMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepo candidateRepo;
    private final SkillRepo skillRepo;
    private final SkillService skillService;
    private final EntityMapper entityMapper;

    public CandidateServiceImpl(CandidateRepo candidateRepo, SkillRepo skillRepo,
                                SkillService skillService, EntityMapper entityMapper) {
        this.candidateRepo = candidateRepo;
        this.skillRepo = skillRepo;
        this.skillService = skillService;
        this.entityMapper = entityMapper;
    }

    @Transactional
    public CandidateResponseDTO addCandidate(CandidateRequestDTO dto) {

        Candidate candidate = new Candidate();

        candidate.setFullName(dto.fullName());
        candidate.setDateOfBirth(dto.dateOfBirth());
        candidate.setContactNumber(dto.contactNumber());
        candidate.setEmail(dto.email());

        Set<Skill> managedSkills = skillService.getSkillsToSet(dto.skills());

        candidate.setSkills(managedSkills);

        Candidate saved = candidateRepo.save(candidate);

        return entityMapper.mapToDTO(saved);
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

    public List<CandidateResponseDTO> getByName(String name) {
        return candidateRepo.findByFullNameContainingIgnoreCase(name)
                .stream()
                .map(entityMapper::mapToDTO)
                .toList();
    }

    public List<CandidateResponseDTO> getBySkill(String skill) {
        return candidateRepo.findBySkill(skill)
                .stream()
                .map(entityMapper::mapToDTO)
                .toList();
    }

    public List<CandidateResponseDTO> getByAllSkills(List<String> skills) {
        return candidateRepo.findByAllSkills(skills, skills.size())
                .stream()
                .map(entityMapper::mapToDTO)
                .toList();
    }
}
