package ucsal.cauzy.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(name = "SolicitacoesPesquisa")
public record SolicitacoesPesquisaDTO(
        Integer idSolicitacoes,
        String descricao,
        LocalDateTime dataHoraSolicitacao,
        LocalDateTime dataHoraAprovacao,
        LocalDateTime dataHoraLocacao,
        UsuarioPesquisaDTO usuarioAvaliador,
        UsuarioPesquisaDTO usuarioSolicitante,
        EspacoFisicoPesquisaDTO espacoFisico,
        StatusDTO status
) {
}
