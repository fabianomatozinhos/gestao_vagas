package gestao_vagas.modules.candidate.controllers;

import gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import gestao_vagas.modules.candidate.entities.CandidateEntity;
import gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import gestao_vagas.modules.candidate.useCases.ListAllJobsByFilteruseCase;
import gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import gestao_vagas.modules.company.entities.JobEntity;
import io.micrometer.core.ipc.http.HttpSender.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Informações do candidato")
public class CandidateController {

    @Autowired //server para injetar a dependencia automaticamente pelo spring
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @Autowired
    private ListAllJobsByFilteruseCase listAllJobsByFilteruseCase;

    @PostMapping("/")
    @Operation(summary = "Cadastro de Candidato", description = "Essa função é responsável por criar um novo candidato")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CandidateEntity.class) )
            }),
            @ApiResponse(responseCode = "400", description = "Usuário já existe")
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
        try {
            var result =  this.createCandidateUseCase.execute(candidateEntity);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')") // Somente candidatos autenticados podem acessar este endpoint
    @Operation(summary = "Perfil do Candidato", description = "Essa função é responsável por listar as informações do perfil do candidato")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class) )
            }),
            @ApiResponse(responseCode = "400", description = "Candidate not found")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> get(HttpServletRequest request ) {
        
        var IdCandidate = request.getAttribute("candidate_id");
        try {
            var profile = profileCandidateUseCase.execute(UUID.fromString(IdCandidate.toString()));
            return ResponseEntity.ok().body(profile);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Listagem de vagas disponível para o candidato", description = "Essa função é responsável por listar todoas as vagas diponiveis filtradas pelo filtro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)) )
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> get(@RequestParam String filter) {
        try {

            var listAllJobs = listAllJobsByFilteruseCase.execute(filter);
            return listAllJobs;

        } catch (Exception e) {
            return null;
        }
    }
}
