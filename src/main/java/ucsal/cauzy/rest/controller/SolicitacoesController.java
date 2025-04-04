package ucsal.cauzy.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.parsers.ReturnTypeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucsal.cauzy.domain.entity.Solicitacoes;
import ucsal.cauzy.domain.service.SolicitacoesService;
import ucsal.cauzy.rest.dto.SolicitacoesDTO;
import ucsal.cauzy.rest.dto.SolicitacoesPesquisaDTO;
import ucsal.cauzy.rest.mapper.SolicitacoesMapper;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("solicitacoes")
@Tag(name = "Solicitações")
@RequiredArgsConstructor
@Slf4j
public class SolicitacoesController implements GenericController {

    private final SolicitacoesService solicitacoesService;

    private final SolicitacoesMapper solicitacoesMapper;

    @GetMapping
    @Operation(summary = "Buscar Todos", description = "Busca Todas As Solicitacões")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso ao buscar todos")
    })
    public ResponseEntity<List<SolicitacoesPesquisaDTO>> findAll() {
        List<SolicitacoesPesquisaDTO> solicitacoesPesquisaDTO = solicitacoesService.findAll()
                .stream()
                .map(solicitacoesMapper::toDTO)
                .toList();
        return ResponseEntity.ok(solicitacoesPesquisaDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar", description = "Buscar Solicitacao Pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Buscar de Solicitacão Feita Com Sucesso"),
            @ApiResponse(responseCode = "404", description = "A Solicitacão Não Foi Encontrada")
    })
    public ResponseEntity<SolicitacoesPesquisaDTO> findByID(@PathVariable Integer id) {
        SolicitacoesPesquisaDTO solicitacoesPesquisaDTO = solicitacoesMapper.
                toDTO(solicitacoesService.findById(id));

        return ResponseEntity.ok(solicitacoesPesquisaDTO);
    }

    @PostMapping
    @Operation(summary = "Salvar", description = "Salvar Solicitacão")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Solicitação Criada Com Sucesso"),
    })
    public ResponseEntity<Void> save(@RequestBody SolicitacoesDTO solicitacoesDTO) {
        Solicitacoes solicitacoes = solicitacoesMapper.toEntity(solicitacoesDTO);
        solicitacoesService.salvar(solicitacoes);
        URI uri = gerarHeaderLocation(solicitacoes.getIdSolicitacoes());
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modificar",description = "Modifica a Solicitacao")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "A solicitação foi modificada"),
            @ApiResponse(responseCode = "404", description = "A Solicitacão não foi encontrada pelo id")
    })
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody SolicitacoesDTO solicitacoesDTO) {
        solicitacoesService.update(id, solicitacoesMapper.toEntity(solicitacoesDTO));

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar",description = "Deletar a Solicitacao")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "A solicitação foi deletada"),
            @ApiResponse(responseCode = "404", description = "A Solicitacão não foi encontrada pelo id")
    })
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        solicitacoesService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}

