package intens.hr.demo.service.impl;

import intens.hr.demo.model.Skill;
import intens.hr.demo.repo.SkillRepo;
import intens.hr.demo.service.SkillService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SkillServiceImpl implements SkillService {

    private  final SkillRepo skillRepo;

    public SkillServiceImpl(SkillRepo skillRepo) {
        this.skillRepo = skillRepo;
    }

    public Skill addSkillOrReturnExisting(String name) {
        return skillRepo.findByName(name)
                .orElseGet(() -> {
                    Skill skill = new Skill();
                    skill.setName(name.toLowerCase().trim());
                    return skillRepo.save(skill);
                });
    }

    public Set<Skill> getSkillsToSet(Set<String> skillNames) {

        Set<Skill> managing = new HashSet<>();

        for (String name : skillNames) {

            Skill skill = addSkillOrReturnExisting(name.toLowerCase().trim());

            managing.add(skill);
        }

        return managing;
    }
}
