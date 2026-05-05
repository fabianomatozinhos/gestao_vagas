package gestao_vagas.modules.candidate.controllers;

import gestao_vagas.modules.candidate.entities.CandidateEntity;
import gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import io.micrometer.core.ipc.http.HttpSender.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired //server para injetar a dependencia automaticamente pelo spring
    private CreateCandidateUseCase createCandidateUseCase;

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

    @GetMapping("")
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("Hello World");
    }
}
