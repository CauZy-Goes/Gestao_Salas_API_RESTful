package ucsal.cauzy.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "Cargo")
public record CargoDTO(
        @Schema(description = "Identificador único do cargo")
        Integer idCargo,

        @NotBlank(message = "campo obrigatorio")
        @Size(min = 4, max = 100, message = "campo fora do tamanho padrao")
        @Schema(name = "nome do cargo")
        String nomeCargo
        ) {
}
