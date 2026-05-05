package gestao_vagas.modules.candidate.useCases;

import gestao_vagas.exceptions.UserFoundException;
import gestao_vagas.modules.candidate.Repository.CandidateRepository;
import gestao_vagas.modules.candidate.entities.CandidateEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public CandidateEntity execute(CandidateEntity candidateEntity) {
        this.candidateRepository
                .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });

        return this.candidateRepository.save(candidateEntity);
    }
}
