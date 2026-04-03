package intens.hr.demo.service;

import intens.hr.demo.model.Skill;

import java.util.Set;

public interface SkillService {

    Skill addSkill(String name);

    Set<Skill> getSkillsToSet(Set<Skill> skills);
}
