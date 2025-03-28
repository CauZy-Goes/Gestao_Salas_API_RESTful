package ucsal.cauzy.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "EspacoFisico")
public record EspacoFisicoDTO(
        @Schema(description = "Identificador único do espaço físico")
        Integer idEspacoFisico,

        @NotNull(message = "Campo obrigatório")
        @Schema(description = "Número do espaço físico")
        Integer numero,

        @Schema(description = "Identificador do tipo de sala")
        Integer idTipoSala
) {
}
