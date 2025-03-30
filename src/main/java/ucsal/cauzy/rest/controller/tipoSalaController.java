package ucsal.cauzy.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucsal.cauzy.domain.entity.TipoSala;
import ucsal.cauzy.domain.service.TipoSalaService;
import ucsal.cauzy.rest.dto.TipoSalaDTO;

import java.util.List;

@RestController
@RequestMapping("/api/tipoSalas")
public class tipoSalaController {

    @Autowired
    private TipoSalaService tipoSalaService;

    // GET /api/tipoSalas - Lista todos os tipoSalas
    @GetMapping
    public ResponseEntity<List<TipoSalaDTO>> getAllTipoSalas() {
        List<TipoSalaDTO> tipoSalas = tipoSalaService.findAll();
        return ResponseEntity.ok(tipoSalas);
    }

    // GET /api/tipoSalas/{id} - Retorna um tipoSala por ID
    @GetMapping("/{id}")
    public ResponseEntity<TipoSala> getTipoSalaById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(tipoSalaService.findById(id));
    }

    // POST /api/tipoSalas - Cria um novo tipoSala
    @PostMapping
    public ResponseEntity<TipoSalaDTO> createTipoSala(@RequestBody TipoSalaDTO tipoSalaDTO) {
        TipoSalaDTO createdTipoSala = tipoSalaService.save(tipoSalaDTO);
        return ResponseEntity.ok(createdTipoSala);
    }

    // PUT /api/tipoSalas/{id} - Atualiza um tipoSala existente
    @PutMapping("/{id}")
    public ResponseEntity<TipoSalaDTO> updateTipoSala(@PathVariable Integer id, @RequestBody TipoSalaDTO tipoSalaDTO) {
        try {
            TipoSalaDTO updatedTipoSala = tipoSalaService.update(id, tipoSalaDTO);
            return ResponseEntity.ok(updatedTipoSala);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/tipoSalas/{id} - Exclui um tipoSala
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipoSala(@PathVariable Integer id) {
        try {
            tipoSalaService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

