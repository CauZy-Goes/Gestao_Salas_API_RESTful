package ucsal.cauzy.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(name = "LogAcoes")
public record LogAcoesDTO(
        @Schema(description = "Identificador único do log de ação")
        Integer idLogAcoes,

        @NotNull(message = "Campo obrigatório")
        @Schema(description = "Data e hora da ação registrada")
        LocalDateTime dataHora,

        @NotNull(message = "Campo obrigatório")
        @Schema(description = "Identificador do usuário que executou a ação")
        Integer idUsuario,

        @NotNull(message = "Campo obrigatório")
        @Schema(description = "Descrição da ação executada")
        String acao
) {
}
