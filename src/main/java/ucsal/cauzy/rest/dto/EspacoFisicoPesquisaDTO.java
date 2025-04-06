package ucsal.cauzy.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "EspacoFisicoPesquisa")
public record EspacoFisicoPesquisaDTO(
        Integer idEspacoFisico,
        Integer numero,
        TipoSalaDTO tipoSala
) {
}
