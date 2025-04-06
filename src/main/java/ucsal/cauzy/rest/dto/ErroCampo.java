package ucsal.cauzy.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ErroCampo")
public record ErroCampo(String campo, String erro) {
}
