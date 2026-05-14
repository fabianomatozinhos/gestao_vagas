package gestao_vagas.modules.candidate.useCases;

import gestao_vagas.exceptions.JobNotFoundException;
import gestao_vagas.exceptions.UserNotFoundException;
import gestao_vagas.modules.candidate.Repository.ApplyJobRepository;
import gestao_vagas.modules.candidate.Repository.CandidateRepository;
import gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import gestao_vagas.modules.candidate.entities.CandidateEntity;
import gestao_vagas.modules.company.entities.JobEntity;
import gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplyJobCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplyJobRepository applyJobRepository;

    public ApplyJobEntity execute(UUID idCandidate, UUID idJob) {
        //validar se o candidato existe
        this.candidateRepository.findById(idCandidate)
                .orElseThrow(() -> {
                    throw new UserNotFoundException();
                });

        //validar se a vaga existe
        this.jobRepository.findById(idJob)
                .orElseThrow(() -> {
                    throw new JobNotFoundException();
                });

        //candidato se inscreve na vaga
        var candidate = new CandidateEntity();
        candidate.setId(idCandidate);
 
        var applyJob = ApplyJobEntity.builder()
                        .candidateEntity(candidate)
                        .jobEntity(JobEntity.builder().id(idJob).build())
                        .build();

        applyJob = applyJobRepository.save(applyJob);

        return applyJob;
    }
}
