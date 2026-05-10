package gestao_vagas.modules.candidate.entities;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "cadidate")
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Schema(example = "Jose maria", requiredMode = Schema.RequiredMode.REQUIRED, description = "Nome do candidato")
    private String name;

    @Pattern(regexp = "\\S+", message = "O campo de username não deve conter espaço")
    private String username;

    @Email(message = "O campo deve conter um e-mail válido")
    private String email;

    @Length(min = 6, max = 100, message = "A senha deve conter entre 6 a 100 caracteres")
    @Schema(example = "admins@123", minLength = 10, maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED, description = "Senha do candidato")
    private String password;
    private String description;
    private String curriculum;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
