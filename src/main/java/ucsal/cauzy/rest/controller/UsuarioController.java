package ucsal.cauzy.rest.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucsal.cauzy.domain.service.CargoService;
import ucsal.cauzy.domain.service.UsuarioService;
import ucsal.cauzy.rest.dto.UsuarioDTO;

import java.util.List;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Controller de usuários")
@Slf4j
public class UsuarioController implements GenericController{

    private final UsuarioService usuarioService;

    // GET /api/usuarios - Lista todos os usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    // GET /api/usuarios/{id} - Retorna um usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(usuarioService.findById(id));
    }

    // GET /api/usarios/email/{email} = Retorna um usuario por email
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioDTO> getUsuarioByEmail(@PathVariable String email) {
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }

        return ResponseEntity.ok(usuarioService.findByEmail(email.trim()));
    }


    // POST /api/usuarios - Cria um novo usuario
    @PostMapping
    public ResponseEntity<UsuarioDTO> createUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO createdUsuario = usuarioService.save(usuarioDTO);
        return ResponseEntity.ok(createdUsuario);
    }

    // PUT /api/usuarios/{id} - Atualiza um usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> updateUsuario(@PathVariable Integer id, @RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioDTO updatedUsuario = usuarioService.update(id, usuarioDTO);
            return ResponseEntity.ok(updatedUsuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/usuarios/{id} - Exclui um usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        try {
            usuarioService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

