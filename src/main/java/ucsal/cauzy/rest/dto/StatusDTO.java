package ucsal.cauzy.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "Status")
public record StatusDTO(
        @Schema(description = "Identificador único do status")
        Integer idStatus,

        @Schema(description = "Nome do status")
        @Size(min = 2, max = 100, message = "campo fora do tamanho padrao")
        @NotBlank(message = "campo obrigatorio")
        String nomeStatus
) {
}
