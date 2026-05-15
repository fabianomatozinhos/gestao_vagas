package gestao_vagas.modules.candidate.useCases;

import gestao_vagas.exceptions.UserFoundException;
import gestao_vagas.modules.candidate.Repository.CandidateRepository;
import gestao_vagas.modules.candidate.entities.CandidateEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public CandidateEntity execute(CandidateEntity candidateEntity) {
        this.candidateRepository
                .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });
        var password = passwordEncoder.encode(
            candidateEntity.getPassword() 
        );

        candidateEntity.setPassword(password);

        return this.candidateRepository.save(candidateEntity);
    }
}
