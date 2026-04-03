package intens.hr.demo.controller;

import intens.hr.demo.dto.CandidateRequestDTO;
import intens.hr.demo.dto.CandidateResponseDTO;
import intens.hr.demo.service.CandidateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @PostMapping
    public CandidateResponseDTO create(@RequestBody CandidateRequestDTO dto) {
        return candidateService.addCandidate(dto);
    }

    @GetMapping("/search/by-name")
    public List<CandidateResponseDTO> searchByName(@RequestParam String name) {
        return candidateService.getByName(name);
    }

    @GetMapping("/search/by-skill")
    public List<CandidateResponseDTO> searchBySkill(@RequestParam String skill) {
        return candidateService.getBySkill(skill);
    }

    @GetMapping("/search/by-skills")
    public List<CandidateResponseDTO> searchByAllSkills(@RequestParam List<String> skill) {
        return candidateService.getByAllSkills(skill);
    }

    @PostMapping("/{id}/skills")
    public CandidateResponseDTO addSkill(
            @PathVariable Long id,
            @RequestParam String skill) {

        return candidateService.addSkillToCandidate(id, skill);
    }

    @DeleteMapping("/{id}/skills")
    public CandidateResponseDTO removeSkill(
            @PathVariable Long id,
            @RequestParam String skill) {

        return candidateService.removeSkillFromCandidate(id, skill);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        candidateService.deleteCandidate(id);
    }
}