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
import org.springframework.web.bind.annotation.*;
import ucsal.cauzy.domain.entity.EspacoFisico;
import ucsal.cauzy.domain.entity.Status;
import ucsal.cauzy.domain.service.EspacoFisicoService;
import ucsal.cauzy.rest.dto.EspacoFisicoDTO;
import ucsal.cauzy.rest.dto.EspacoFisicoPesquisaDTO;
import ucsal.cauzy.rest.dto.StatusDTO;
import ucsal.cauzy.rest.dto.EspacoFisicoDTO;
import ucsal.cauzy.rest.mapper.EspacoFisicoMapper;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("espacoFisico")
@Slf4j
@RequiredArgsConstructor
@Tag(name="Espaco Físico")
public class EspacoFisicoController implements GenericController{

    private final EspacoFisicoService espacoFisicoService;

    private final EspacoFisicoMapper espacoFisicoMapper;

    @GetMapping
    @Operation(summary = "Buscar todos", description = "Buscar todos os Espacos Fisicos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Espacos Fisicos Listados com Sucesso")
    })
    public ResponseEntity<List<EspacoFisicoPesquisaDTO>> findAll() {
        List<EspacoFisicoPesquisaDTO> listaEspacos = espacoFisicoService.findAll()
                .stream()
                .map(espacoFisicoMapper::toDTO)
                .toList();

        return ResponseEntity.ok(listaEspacos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pelo ID", description = "Buscar Espaco Físico pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Espaco Físico encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Espaco Físico não encontrado")
    })
    public ResponseEntity<EspacoFisicoPesquisaDTO> findById(@PathVariable Integer id){
        EspacoFisicoPesquisaDTO espacoFisicoPesquisaDTO = espacoFisicoMapper.toDTO(espacoFisicoService.findById(id));
        return ResponseEntity.ok(espacoFisicoPesquisaDTO);
    }

    @PostMapping
    @Operation(summary = "Salvar", description = "Salvar Espaco Físico")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Espaco Físico Criado Com Sucesso"),
            @ApiResponse(responseCode = "409", description = "Esse número de espaco Fisico Já foi cadastrado")
    })
    public ResponseEntity<Void> save(@RequestBody @Valid EspacoFisicoDTO espacoFisicoDTO){
         EspacoFisico espacoFisico = espacoFisicoMapper.toEntity(espacoFisicoDTO);
        espacoFisicoService.save(espacoFisico);
        URI uri =  gerarHeaderLocation(espacoFisico.getIdEspacoFisico());
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update", description = "Modificar Espaco Físico")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Espaco Físico Modificado Com Sucesso"),
            @ApiResponse(responseCode = "404", description = "Espaco Físico Não encontrado"),
            @ApiResponse(responseCode = "409", description = "O nuemero que foi modificado esta em uso"),
    })
    public ResponseEntity<Void> update(@RequestBody @Valid EspacoFisicoDTO espacoFisicoDTO, @PathVariable Integer id){
        espacoFisicoService.update(espacoFisicoMapper.toEntity(espacoFisicoDTO) , id);

        return ResponseEntity.noContent().build();
    }
}

