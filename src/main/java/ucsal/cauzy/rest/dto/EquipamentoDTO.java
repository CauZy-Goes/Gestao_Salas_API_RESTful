package ucsal.cauzy.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "Equipamento")
public record EquipamentoDTO(
        @Schema(description = "Identificador único do equipamento")
        Integer idEquipamento,

        @NotBlank(message = "Campo obrigatório")
        @Schema(description = "Nome do equipamento")
        String nomeEquipamento,

        @NotNull(message = "Campo obrigatório")
        @Schema(description = "Identificador do espaço físico onde o equipamento está localizado")
        Integer idEspacoFisico
) {
}
