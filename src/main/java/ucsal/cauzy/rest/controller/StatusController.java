package ucsal.cauzy.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucsal.cauzy.domain.entity.Status;
import ucsal.cauzy.domain.service.StatusService;
import ucsal.cauzy.rest.dto.StatusDTO;
import ucsal.cauzy.rest.mapper.StatusMapper;

import java.util.List;

@RestController
@RequestMapping("status")
@RequiredArgsConstructor
@Tag(name = "Status")
@Slf4j
public class StatusController {

    private final StatusService statusService;

    private final StatusMapper statusMapper;

    @GetMapping
    @Operation(summary = "Buscar todos", description = "Buscar todos os status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cargos Listados com Sucesso")
    })
    public ResponseEntity<List<StatusDTO>> findAll() {
        List<StatusDTO> listaStatus = statusService.findAll()
                .stream()
                .map(statusMapper::toDTO)
                .toList();

        return ResponseEntity.ok(listaStatus);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pelo ID", description = "Buscar status pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cargo encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cargo não encontrado")
    })
    public ResponseEntity<StatusDTO> findById(@PathVariable Integer id){
        StatusDTO statusDTO = statusMapper.toDTO(statusService.findById(id));
        return ResponseEntity.ok(statusDTO);
    }

    // POST /api/status - Cria um novo status
    @PostMapping
    public ResponseEntity<StatusDTO> createStatus(@RequestBody StatusDTO statusDTO) {
        StatusDTO createdStatus = statusService.save(statusDTO);
        return ResponseEntity.ok(createdStatus);
    }

    // PUT /api/status/{id} - Atualiza um status existente
    @PutMapping("/{id}")
    public ResponseEntity<StatusDTO> updateStatus(@PathVariable Integer id, @RequestBody StatusDTO statusDTO) {
        try {
            StatusDTO updatedStatus = statusService.update(id, statusDTO);
            return ResponseEntity.ok(updatedStatus);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/status/{id} - Exclui um status
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatus(@PathVariable Integer id) {
        try {
            statusService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

