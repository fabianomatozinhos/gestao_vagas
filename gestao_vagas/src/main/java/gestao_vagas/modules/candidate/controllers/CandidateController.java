package gestao_vagas.modules.candidate.controllers;

import gestao_vagas.modules.candidate.entities.CandidateEntity;
import gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import io.micrometer.core.ipc.http.HttpSender.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired //server para injetar a dependencia automaticamente pelo spring
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
        System.out.println("Candidato");
        System.out.println("Candidato " + candidateEntity.getName());

        try {
            var result =  this.createCandidateUseCase.execute(candidateEntity);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')") // Somente candidatos autenticados podem acessar este endpoint
    public ResponseEntity<Object> get(HttpServletRequest request ) {
        
        var IdCandidate = request.getAttribute("candidate_id");
        try {
            var profile = profileCandidateUseCase.execute(UUID.fromString(IdCandidate.toString()));
            return ResponseEntity.ok().body(profile);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("Hello World");
    }
}
