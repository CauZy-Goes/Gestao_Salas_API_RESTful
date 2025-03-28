package ucsal.cauzy.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "TipoSala")
public record TipoSalaDTO(
        @Schema(description = "Identificador único do tipo de sala")
        Integer idTipoSala,

        @NotBlank(message = "Campo obrigatorio")
        @Size(min = 2, max = 100, message = "campo fora do tamanho padrao")
        @Schema(description = "Nome da sala")
        String nomeSala
) {
}
