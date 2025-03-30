package ucsal.cauzy.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record EspacoFisicoPesquisa(
        Integer idEspacoFisico,
        Integer numero,
        TipoSalaDTO tipoSalaDTO
) {
}
