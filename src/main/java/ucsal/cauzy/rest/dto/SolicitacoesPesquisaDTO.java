package ucsal.cauzy.rest.dto;

import java.time.LocalDateTime;

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
