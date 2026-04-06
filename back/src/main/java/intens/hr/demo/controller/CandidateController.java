package intens.hr.demo.controller;

import intens.hr.demo.dto.CandidateRequestDTO;
import intens.hr.demo.dto.CandidateResponseDTO;
import intens.hr.demo.service.CandidateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidates")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Candidate API", description = "Operations related to candidates")
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @PostMapping
    @Operation(summary = "Create a new candidate")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Candidate created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public CandidateResponseDTO create(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Candidate data",
            required = true) CandidateRequestDTO dto) {
        return candidateService.addCandidate(dto);
    }

    @GetMapping("/search/by-name")
    @Operation(summary = "Search candidates by name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully fetched results"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public List<CandidateResponseDTO> searchByName(@Parameter(description = "Name to search") @RequestParam String name) {
        return candidateService.getByName(name);
    }

    @GetMapping("/search/by-skill")
    @Operation(summary = "Find candidates by a single skill")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully fetched results"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public List<CandidateResponseDTO> searchBySkill(@Parameter(description = "Skill name") @RequestParam String skill) {
        return candidateService.getBySkill(skill);
    }

    @GetMapping("/search/by-skills")
    @Operation(summary = "Find candidates by a combination of skills")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully fetched results"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public List<CandidateResponseDTO> searchByAllSkills(@Parameter(description = "List of skill names") @RequestParam List<String> skill) {
        return candidateService.getByAllSkills(skill);
    }

    @PostMapping("/{id}/skills")
    @Operation(summary = "Add skill to candidate")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Skill added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public CandidateResponseDTO addSkill(
            @PathVariable Long id,
            @RequestParam String skill) {

        return candidateService.addSkillToCandidate(id, skill);
    }

    @DeleteMapping("/{id}/skills")
    @Operation(summary = "Remove skill from candidate")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Skill removed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public CandidateResponseDTO removeSkill(
            @PathVariable Long id,
            @RequestParam String skill) {

        return candidateService.removeSkillFromCandidate(id, skill);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete candidate")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Candidate deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public void delete(@PathVariable Long id) {
        candidateService.deleteCandidate(id);
    }
}