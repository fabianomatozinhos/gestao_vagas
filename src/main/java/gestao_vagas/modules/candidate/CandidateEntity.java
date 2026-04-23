package gestao_vagas.modules.candidate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Data
public class CandidateEntity {

    private UUID id;
    private String name;

    @Pattern(regexp = "\\S+", message = "O campo de username não deve conter espaço")
    private String username;

    @Email(message = "O campo deve conter um e-mail válido")
    private String email;

    @Length(min = 6, max = 100, message = "A senha deve conter entre 6 a 100 caracteres")
    private String password;
    private String description;
    private String curriculum;

}
