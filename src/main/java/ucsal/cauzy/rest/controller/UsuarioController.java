package ucsal.cauzy.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;
import ucsal.cauzy.domain.entity.Usuario;
import ucsal.cauzy.domain.service.CargoService;
import ucsal.cauzy.domain.service.UsuarioService;
import ucsal.cauzy.rest.dto.UsuarioDTO;
import ucsal.cauzy.rest.dto.UsuarioPesquisaDTO;
import ucsal.cauzy.rest.mapper.UsuarioMapper;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Controller de usuários")
@Slf4j
public class UsuarioController implements GenericController{

    private final UsuarioService usuarioService;

    private final UsuarioMapper usuarioMapper;
    private final HandlerMapping resourceHandlerMapping;

    @GetMapping
    @Operation(summary = "Buscar Todos", description = "Busca todos os usuarios")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Busca efetuada com sucesso")
    })
    public ResponseEntity<List<UsuarioPesquisaDTO>> findAll() {
        List<UsuarioPesquisaDTO> usuarios = usuarioService.findAll()
                                            .stream()
                                            .map(usuarioMapper::toDTO)
                                            .toList();

        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar", description = "Busca o usuário por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Busca efetuada com sucesso"),
            @ApiResponse(responseCode = "404", description = "O Usuario não foi encontrado"),
    })
    public ResponseEntity<UsuarioPesquisaDTO> findById(@PathVariable Integer id) {
        UsuarioPesquisaDTO usuario = usuarioMapper.toDTO(usuarioService.findById(id));

        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    @Operation(summary = "Salvar", description = "Salvar Usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario Salvo com Sucesso"),
            @ApiResponse(responseCode = "409", description = "Esse email já foi cadastrado")
    })
    public ResponseEntity<Void> save(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioService.save(usuarioMapper.toEntity(usuarioDTO));

        URI location = gerarHeaderLocation(usuario.getIdUsuario());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update", description = "Modificificar Usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário Modificado Com Sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário Não encontrado"),
            @ApiResponse(responseCode = "409", description = "O email que foi modificado esta em uso"),
    })
    public ResponseEntity<Void> update(@RequestBody @Valid UsuarioDTO usuarioDTO, @PathVariable Integer id){
        usuarioService.update(usuarioMapper.toEntity(usuarioDTO) , id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar", description = "Deletar Usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuario Deletado Com Sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

