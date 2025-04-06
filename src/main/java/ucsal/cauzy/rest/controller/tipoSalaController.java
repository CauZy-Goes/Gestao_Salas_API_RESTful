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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ucsal.cauzy.domain.entity.TipoSala;
import ucsal.cauzy.domain.service.TipoSalaService;
import ucsal.cauzy.rest.dto.TipoSalaDTO;
import ucsal.cauzy.rest.mapper.TipoSalaMapper;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("tipoSala")
@RequiredArgsConstructor
@Tag(name="TipoSala")
@Slf4j
public class tipoSalaController implements GenericController {

    private final TipoSalaService tipoSalaService;

    private final TipoSalaMapper tipoSalaMapper;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Pelo Id", description = "Busca O Tipo De Sala Pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso na busca"),
            @ApiResponse(responseCode = "404", description = "Tipo De Sala Não foi encontrado")
    })
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<TipoSalaDTO> findById(@PathVariable Integer id) {
        TipoSala tipoSala = tipoSalaService.findById(id);
        return ResponseEntity.ok(tipoSalaMapper.toDTO(tipoSala));
    }

    @GetMapping
    @Operation(summary = "Buscar Todos", description = "Buscar Todos Os Tipos De Salas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso Na Busca")
    })
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<List<TipoSalaDTO>> findAll() {
        List<TipoSalaDTO> tipoSalas = tipoSalaService.findAll()
                .stream()
                .map(tipoSalaMapper::toDTO)
                .toList();

        return ResponseEntity.ok(tipoSalas);
    }

    @PostMapping
    @Operation(summary = "Salvar", description = "Salvar um novo Tipo De Sala")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tipo Sala Criado Com Sucesso")
    })
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<Void> save(@RequestBody @Valid TipoSalaDTO tipoSalaDto) {
        TipoSala tipoSala = tipoSalaMapper.toEntity(tipoSalaDto);
        tipoSalaService.save(tipoSala);
        URI uri = gerarHeaderLocation(tipoSala.getIdTipoSala());
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modificar", description = "Modificar Tipo De Sala")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tipo Sala Modificado Com Sucesso"),
            @ApiResponse(responseCode = "404", description = "Tipo Sala Não Encontrado"),
    })
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<Void> update(@RequestBody @Valid TipoSalaDTO tipoSalaDto, @PathVariable Integer id) {
        TipoSala tipoSala = tipoSalaMapper.toEntity(tipoSalaDto);
        tipoSalaService.update(tipoSala, id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Modificar", description = "Modificar Tipo De Sala")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tipo Sala Deletado Com Sucesso"),
            @ApiResponse(responseCode = "404", description = "Tipo Sala Não Encontrado"),
            @ApiResponse(responseCode = "409", description = "Alguma Entidade Depende Desse Tipo Sala")
    })
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        tipoSalaService.delete(id);

        return ResponseEntity.noContent().build();
    }
}

