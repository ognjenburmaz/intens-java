package intens.hr.demo.repo;

import intens.hr.demo.model.Candidate;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepo extends JpaRepository<Candidate, Long> {

    @EntityGraph(attributePaths = "skills")
    List<Candidate> findByFullNameContainingIgnoreCase(String name);

    @Query("""
    SELECT c.id FROM Candidate c
    JOIN c.skills s
    WHERE s.name IN :skills
    GROUP BY c.id
    HAVING COUNT(DISTINCT s.id) = :size
    """)
    List<Long> findSkillIdsByName(List<String> skills, long size);

    @Query("""
    SELECT DISTINCT c FROM Candidate c
    JOIN FETCH c.skills
    WHERE c.id IN :ids
    """)
    List<Candidate> findSkillsByIds(List<Long> ids);

    @Query("""
    SELECT DISTINCT c FROM Candidate c
    JOIN FETCH c.skills s
    WHERE s.name = :skillName
    """)
    List<Candidate> findBySkill(String skillName);
}
