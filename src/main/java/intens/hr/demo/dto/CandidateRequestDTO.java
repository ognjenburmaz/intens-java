package intens.hr.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

@Schema(description = "Candidate request")
public record CandidateRequestDTO(@Schema(example = "Test Test") @NonNull String fullName,
                                  @Schema(example = "2003-05-28") @DateTimeFormat LocalDate dateOfBirth,
                                  @Schema(example = "+123456789") String contactNumber,
                                  @Schema(example = "test@gmail.com") @Email String email,
                                  @Schema(example = "[\"java\", \"sql\"]") Set<String> skills) {}
