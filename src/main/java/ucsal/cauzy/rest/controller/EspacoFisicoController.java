package ucsal.cauzy.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucsal.cauzy.domain.service.EspacoFisicoService;
import ucsal.cauzy.rest.dto.EspacoFisicoDTO;

import java.util.List;

@RestController
@RequestMapping("/api/espacoFisicos")
public class EspacoFisicoController {

    @Autowired
    private EspacoFisicoService espacoFisicoService;

    // GET /api/espacoFisicos - Lista todos os espacoFisicos
    @GetMapping
    public ResponseEntity<List<EspacoFisicoDTO>> getAllEspacoFisicos() {
        List<EspacoFisicoDTO> espacoFisicos = espacoFisicoService.findAll();
        return ResponseEntity.ok(espacoFisicos);
    }

    // GET /api/espacoFisicos/{id} - Retorna um espacoFisico por ID
    @GetMapping("/{id}")
    public ResponseEntity<EspacoFisicoDTO> getEspacoFisicoById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(espacoFisicoService.findById(id));
    }

    // GET /api/espacoFisicos/numero/{numero} - Retorna um espacoFisico pelo número
    @GetMapping("/numero/{numero}")
    public ResponseEntity<EspacoFisicoDTO> getEspacoFisicoByNumero(@PathVariable Integer numero) {
        EspacoFisicoDTO espacoFisicoDTO = espacoFisicoService.findByNumero(numero);
        return ResponseEntity.ok(espacoFisicoDTO);
    }

    // POST /api/espacoFisicos - Cria um novo espacoFisico
    @PostMapping
    public ResponseEntity<EspacoFisicoDTO> createEspacoFisico(@RequestBody EspacoFisicoDTO espacoFisicoDTO) {
        EspacoFisicoDTO createdEspacoFisico = espacoFisicoService.save(espacoFisicoDTO);
        return ResponseEntity.ok(createdEspacoFisico);
    }

    // PUT /api/espacoFisicos/{id} - Atualiza um espacoFisico existente
    @PutMapping("/{id}")
    public ResponseEntity<EspacoFisicoDTO> updateEspacoFisico(@PathVariable Integer id, @RequestBody EspacoFisicoDTO espacoFisicoDTO) {
        try {
            EspacoFisicoDTO updatedEspacoFisico = espacoFisicoService.update(id, espacoFisicoDTO);
            return ResponseEntity.ok(updatedEspacoFisico);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/espacoFisicos/{id} - Exclui um espacoFisico
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEspacoFisico(@PathVariable Integer id) {
        try {
            espacoFisicoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

