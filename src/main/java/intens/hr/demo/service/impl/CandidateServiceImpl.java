package intens.hr.demo.service.impl;

import intens.hr.demo.repo.CandidateRepo;
import intens.hr.demo.repo.SkillRepo;
import intens.hr.demo.service.CandidateService;
import org.springframework.stereotype.Service;

@Service
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepo candidateRepo;
    private final SkillRepo skillRepo;

    public CandidateServiceImpl(CandidateRepo candidateRepo,  SkillRepo skillRepo) {
        this.candidateRepo = candidateRepo;
        this.skillRepo = skillRepo;
    }
}
