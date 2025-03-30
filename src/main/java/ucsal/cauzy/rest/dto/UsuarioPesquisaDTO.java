package ucsal.cauzy.rest.dto;

public record UsuarioPesquisaDTO(
        Integer idUsuario,
        String nomeUsuario,
        String email,
        String senha,
        CargoDTO cargo
        ) {
}
