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
import ucsal.cauzy.domain.entity.Solicitacoes;
import ucsal.cauzy.domain.service.SolicitacoesService;
import ucsal.cauzy.rest.dto.SolicitacoesDTO;
import ucsal.cauzy.rest.dto.SolicitacoesPesquisaDTO;
import ucsal.cauzy.rest.mapper.SolicitacoesMapper;

import java.util.List;

@RestController
@RequestMapping("solicitacoes")
@Tag(name = "Solicitações")
@RequiredArgsConstructor
@Slf4j
public class SolicitacoesController implements GenericController {

    private final SolicitacoesService solicitacoesService;

    private final SolicitacoesMapper solicitacoesMapper;

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
}

