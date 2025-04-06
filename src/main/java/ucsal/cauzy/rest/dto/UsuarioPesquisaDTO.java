package ucsal.cauzy.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UsuarioPesquisa")
public record UsuarioPesquisaDTO(
        Integer idUsuario,
        String nomeUsuario,
        String email,
        String senha,
        CargoDTO cargo
        ) {
}
