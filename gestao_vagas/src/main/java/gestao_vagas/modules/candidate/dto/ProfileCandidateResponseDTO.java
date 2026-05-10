package gestao_vagas.modules.candidate.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCandidateResponseDTO {

    @Schema(example = "Desenvolvedor Java")
    private String description;

    @Schema(example = "jose_maria")
    private String username;

    @Schema(example = "josemaria@email.com")
    private String email;

    private String curriculum;

    @Schema(example = "José Maria")
    private String name;
    private UUID id;
}
