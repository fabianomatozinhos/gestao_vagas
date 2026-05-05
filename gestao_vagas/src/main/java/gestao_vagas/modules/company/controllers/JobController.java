package gestao_vagas.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gestao_vagas.modules.company.entities.JobEntity;
import gestao_vagas.modules.company.useCases.CreateJobyUseCase;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private CreateJobyUseCase createJobyUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> create (@Valid @RequestBody JobEntity jobEntity){
        try {
            var result = this.createJobyUseCase.execute(jobEntity);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
