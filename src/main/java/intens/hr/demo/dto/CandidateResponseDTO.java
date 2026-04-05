package intens.hr.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

@Schema(description = "Candidate response")
public record CandidateResponseDTO(Long id,
                                   @NonNull String fullName,
                                   @DateTimeFormat LocalDate dateOfBirth,
                                   String contactNumber,
                                   @Email String email,
                                   Set<String> skills) {}
