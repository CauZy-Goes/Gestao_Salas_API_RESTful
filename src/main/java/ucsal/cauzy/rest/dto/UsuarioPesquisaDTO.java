package ucsal.cauzy.rest.dto;

public record UsuarioPesquisaDTO(
        Integer idUsuario,
        String nomeUsario,
        String email,
        String senha,
        CargoDTO cargo
        ) {
}
