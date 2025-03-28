package ucsal.cauzy.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(name = "Solicitacoes")
public record SolicitacoesDTO(
        @Schema(description = "Identificador único da solicitação")
        Integer idSolicitacoes,

        @Schema(description = "Descrição da solicitação")
        String descricao,

        @NotNull(message = "Campo obrigatório")
        @Schema(description = "Data e hora da solicitação")
        LocalDateTime dataHoraSolicitacao,

        @Schema(description = "Data e hora da aprovação da solicitação")
        LocalDateTime dataHoraAprovacao,

        @NotNull(message = "Campo obrigatório")
        @Schema(description = "Data e hora da locação do espaço físico")
        LocalDateTime dataHoraLocacao,

        @Schema(description = "Identificador do avaliador da solicitação")
        Integer idUsuarioAvaliador,

        @NotNull(message = "Campo obrigatório")
        @Schema(description = "Identificador do solicitante da solicitação")
        Integer idUsuarioSolicitante,

        @NotNull(message = "Campo obrigatório")
        @Schema(description = "Identificador do espaço físico associado à solicitação")
        Integer idEspacoFisico,

        @NotNull(message = "Campo obrigatório")
        @Schema(description = "Identificador do status da solicitação")
        Integer idStatus
) {
}
