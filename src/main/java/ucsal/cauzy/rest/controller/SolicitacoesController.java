package ucsal.cauzy.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ucsal.cauzy.domain.entity.EspacoFisico;
import ucsal.cauzy.domain.entity.Solicitacoes;
import ucsal.cauzy.domain.entity.Status;
import ucsal.cauzy.domain.entity.Usuario;
import ucsal.cauzy.domain.repository.EspacoFisicoRepository;
import ucsal.cauzy.domain.repository.StatusRepository;
import ucsal.cauzy.domain.repository.UsuarioRepository;
import ucsal.cauzy.domain.service.SolicitacoesService;
import ucsal.cauzy.rest.dto.SolicitacoesDTO;
import ucsal.cauzy.rest.dto.SolicitacoesPesquisaDTO;
import ucsal.cauzy.rest.mapper.SolicitacoesMapper;

import java.net.URI;

@RestController
@RequestMapping("solicitacoes")
@Tag(name = "Solicitações")
@RequiredArgsConstructor
@Slf4j
public class SolicitacoesController implements GenericController {

    private final SolicitacoesService solicitacoesService;

    private final SolicitacoesMapper solicitacoesMapper;

    private final UsuarioRepository usuarioRepository;

    private final EspacoFisicoRepository espacoFisicoRepository;

    private final StatusRepository statusRepository;

//    @GetMapping
//    @Operation(summary = "Buscar Todos", description = "Busca Todas As Solicitacões")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "Sucesso ao buscar todos")
//    })
//    public ResponseEntity<List<SolicitacoesPesquisaDTO>> findAll() {
//        List<SolicitacoesPesquisaDTO> solicitacoesPesquisaDTO = solicitacoesService.findAll()
//                .stream()
//                .map(solicitacoesMapper::toDTO)
//                .toList();
//        return ResponseEntity.ok(solicitacoesPesquisaDTO);
//    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar", description = "Buscar Solicitacao Pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Buscar de Solicitacão Feita Com Sucesso"),
            @ApiResponse(responseCode = "404", description = "A Solicitacão Não Foi Encontrada")
    })
    @PreAuthorize("hasAnyRole('PROFESSOR', 'GESTOR')")
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
    @PreAuthorize("hasRole('PROFESSOR')")
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
    @PreAuthorize("hasAnyRole('PROFESSOR', 'GESTOR')")
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
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        solicitacoesService.excluir(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Pesquisar", description = "Pesquisa solicitacões")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "As solicitacões foram pesquisadas")
    })
    @PreAuthorize("hasAnyRole('PROFESSOR', 'GESTOR')")
    public ResponseEntity<Page<SolicitacoesPesquisaDTO>> pesquisa(
            @RequestParam(value = "usuarioAvaliador", required = false)
            Integer idUsuarioAvaliador,
            @RequestParam(value = "usuarioSolicitante", required = false)
            Integer idUsuarioSolicitante,
            @RequestParam(value = "espacoFisico", required = false)
            Integer idEspacoFisico,
            @RequestParam(value = "status", required = false)
            Integer idStatus,
            @RequestParam(value = "pagina", defaultValue = "0")
            Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10")
            Integer tamanhoPagina
    ) {
        Page<Solicitacoes> paginaResultado = solicitacoesService.pesquisa
                (idUsuarioAvaliador,  idUsuarioSolicitante,  idEspacoFisico,  idStatus,  pagina, tamanhoPagina);

        Page<SolicitacoesPesquisaDTO> resultado = paginaResultado.map(solicitacoesMapper::toDTO);

        return ResponseEntity.ok(resultado);
    }
}

