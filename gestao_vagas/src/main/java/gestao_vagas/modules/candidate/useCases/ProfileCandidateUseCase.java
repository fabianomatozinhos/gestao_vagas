package gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import gestao_vagas.modules.candidate.Repository.CandidateRepository;
import gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import gestao_vagas.modules.candidate.entities.CandidateEntity;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute (UUID IdCandidate) {
        
        var candidate = this.candidateRepository.findById(IdCandidate)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Candidate not found");
                });
        
        var profileCandidateDTO = ProfileCandidateResponseDTO.builder()
                                .description(candidate.getDescription())
                                .username(candidate.getUsername())        
                                .email(candidate.getEmail())
                                .name(candidate.getName())
                                .curriculum(candidate.getCurriculum())
                                .id(candidate.getId())
                                .build();
        return profileCandidateDTO;
    }

}
