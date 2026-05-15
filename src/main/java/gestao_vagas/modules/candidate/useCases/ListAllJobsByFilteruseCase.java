package gestao_vagas.modules.candidate.useCases;

import gestao_vagas.modules.company.entities.JobEntity;
import gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListAllJobsByFilteruseCase {

    @Autowired
    private JobRepository jobRepository;

    public List<JobEntity> execute(String filter) {
       return this.jobRepository.findByDescriptionContainingIgnoreCase(filter);
    }
}
