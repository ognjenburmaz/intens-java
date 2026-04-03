package intens.hr.demo.repo;

import intens.hr.demo.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepo extends JpaRepository<Candidate, Long> {

    List<Candidate> findByFullNameContainingIgnoreCase(String name);

    @Query("""
    SELECT c FROM Candidate c
    JOIN c.skills s
    WHERE s.name IN :skills
    GROUP BY c
    HAVING COUNT(DISTINCT s.id) = :size
    """)
    List<Candidate> findByAllSkills(List<String> skills, long size);

    @Query("""
    SELECT c FROM Candidate c
    JOIN c.skills s
    WHERE s.name = :skillName
    """)
    List<Candidate> findBySkill(String skillName);
}
