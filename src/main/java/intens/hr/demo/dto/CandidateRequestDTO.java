package intens.hr.demo.dto;

import java.time.LocalDate;
import java.util.Set;

public record CandidateRequestDTO(String fullName,
                                  LocalDate dateOfBirth,
                                  String contactNumber,
                                  String email,
                                  Set<String> skills) {}
