package ucsal.cauzy.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(name = "Usuario")
public record UsuarioDTO(
        @Schema(description = "Identificador único do usuário")
        Integer idUsuario,

        @NotBlank(message = "Campo obrigatório")
        @Size(min = 3, max = 50, message = "Fora Do Tamanho Padrao")
        @Schema(description = "Nome completo do usuário")
        String nomeUsuario,

        @NotBlank(message = "Campo obrigatório")
        @Email(message = "Email inválido")
        @Schema(description = "Email do usuário")
        String email,

        @NotBlank(message = "Campo obrigatório")
        @Schema(description = "Senha do usuário")
        String senha,

        @NotNull(message = "Campo obrigatório")
        @Schema(description = "Identificador do cargo do usuário")
        Integer idCargo
) {
}
