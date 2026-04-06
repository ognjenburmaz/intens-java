package intens.hr.demo.repo;

import intens.hr.demo.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillRepo extends JpaRepository<Skill, Long> {

    Optional<Skill> findByName(String name);
}
