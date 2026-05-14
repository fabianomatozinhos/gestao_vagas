package gestao_vagas.modules.company.useCases;

import gestao_vagas.exceptions.CompanyNotFoundException;
import gestao_vagas.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gestao_vagas.modules.company.entities.JobEntity;
import gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class CreateJobyUseCase {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public JobEntity execute (JobEntity jobEntity){
        companyRepository.findById(jobEntity.getCompanyEntity().getId())
                .orElseThrow(() -> {
                    throw new CompanyNotFoundException();
                });

        return this.jobRepository.save(jobEntity);
    }
}
