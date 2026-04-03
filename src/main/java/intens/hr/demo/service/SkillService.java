package intens.hr.demo.service;

import intens.hr.demo.model.Skill;

import java.util.Set;

public interface SkillService {

    Skill addSkillOrReturnExisting(String name);

    Set<Skill> getSkillsToSet(Set<String> skillNames);
}
