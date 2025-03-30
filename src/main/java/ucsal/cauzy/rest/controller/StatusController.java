package ucsal.cauzy.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucsal.cauzy.domain.entity.Status;
import ucsal.cauzy.domain.service.StatusService;
import ucsal.cauzy.rest.dto.StatusDTO;

import java.util.List;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    @Autowired
    private StatusService statusService;

    // GET /api/status - Lista todos os status
    @GetMapping
    public ResponseEntity<List<StatusDTO>> getAllStatus() {
        List<StatusDTO> status = statusService.findAll();
        return ResponseEntity.ok(status);
    }

    // GET /api/status/{id} - Retorna um status por ID
    @GetMapping("/{id}")
    public ResponseEntity<Status> getStatusById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(statusService.findById(id));
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

