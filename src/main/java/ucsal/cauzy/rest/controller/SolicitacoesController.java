package ucsal.cauzy.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucsal.cauzy.domain.service.SolicitacoesService;
import ucsal.cauzy.rest.dto.SolicitacoesDTO;

import java.util.List;

@RestController
@RequestMapping("/api/solicitacoes")
public class SolicitacoesController {

    @Autowired
    private SolicitacoesService solicitacoesService;

    // GET /api/solicitacoes - Lista todos os solicitacoes
    @GetMapping
    public ResponseEntity<List<SolicitacoesDTO>> getAllSolicitacoess() {
        List<SolicitacoesDTO> solicitacoes = solicitacoesService.findAll();
        return ResponseEntity.ok(solicitacoes);
    }

    @GetMapping("/solicitante/{id}")
    public ResponseEntity<List<SolicitacoesDTO>> getSolicitacoesDoSolicitante(@PathVariable int id) {
        List<SolicitacoesDTO> solicitacoes = solicitacoesService.findByUsuarioSolicitanteId(id);
        return ResponseEntity.ok(solicitacoes);
    }

    // GET /api/solicitacoes/{id} - Retorna um solicitacoes por ID
    @GetMapping("/{id}")
    public ResponseEntity<SolicitacoesDTO> getSolicitacoesById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(solicitacoesService.findById(id));
    }

    // POST /api/solicitacoes - Cria um novo solicitacoes
    @PostMapping
    public ResponseEntity<SolicitacoesDTO> createSolicitacoes(@RequestBody SolicitacoesDTO solicitacoesDTO) {
        SolicitacoesDTO createdSolicitacoes = solicitacoesService.save(solicitacoesDTO);
        return ResponseEntity.ok(createdSolicitacoes);
    }

    // PUT /api/solicitacoes/{id} - Atualiza um solicitacoes existente
    @PutMapping("/{id}")
    public ResponseEntity<SolicitacoesDTO> updateSolicitacoes(@PathVariable Integer id, @RequestBody SolicitacoesDTO solicitacoesDTO) {
        try {
            SolicitacoesDTO updatedSolicitacoes = solicitacoesService.update(id, solicitacoesDTO);
            return ResponseEntity.ok(updatedSolicitacoes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/solicitacoes/{id} - Exclui um solicitacoes
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSolicitacoes(@PathVariable Integer id) {
        try {
            solicitacoesService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

