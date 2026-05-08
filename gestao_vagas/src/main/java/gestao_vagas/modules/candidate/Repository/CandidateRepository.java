package gestao_vagas.modules.candidate.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gestao_vagas.modules.candidate.entities.CandidateEntity;

import java.util.Optional;
import java.util.UUID;

public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID> {

    Optional<CandidateEntity> findByUsernameOrEmail(String username, String email);

    Optional<CandidateEntity> findByUsername(String username);

}
