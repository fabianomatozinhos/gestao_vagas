package gestao_vagas.modules.company.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gestao_vagas.modules.company.dto.CreateJobDTO;
import gestao_vagas.modules.company.entities.JobEntity;
import gestao_vagas.modules.company.repositories.CompanyRepository;
import gestao_vagas.modules.company.useCases.CreateJobyUseCase;
import jakarta.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping("/company/job")
public class JobController {

    @Autowired
    private CreateJobyUseCase createJobyUseCase;

    @Autowired
    private CompanyRepository companyRepository;

    @PostMapping("/")
    @PreAuthorize("hasRole('COMPANY')") // Somente empresas autenticadas podem acessar este endpoint
    public ResponseEntity<Object> create (@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {
        try {
            var companyId = request.getAttribute("company_id");

            var company = this.companyRepository.findById(UUID.fromString(companyId.toString()))
                    .orElseThrow(() -> new RuntimeException("Company not found"));

            
            // jobEntity.setCompanyEntity(company);

            JobEntity jobEntity = JobEntity.builder()
                .benefits(createJobDTO.getBenefits())
                .description(createJobDTO.getDescription())
                .companyEntity(company)
                .level(createJobDTO.getLevel())
                .build();


            var result = this.createJobyUseCase.execute(jobEntity);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
