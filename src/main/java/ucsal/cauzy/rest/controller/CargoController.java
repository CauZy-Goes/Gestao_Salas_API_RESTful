package ucsal.cauzy.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucsal.cauzy.domain.entity.Cargo;
import ucsal.cauzy.domain.service.CargoService;
import ucsal.cauzy.rest.dto.CargoDTO;
import ucsal.cauzy.rest.mapper.CargoMapper;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("cargos")
@RequiredArgsConstructor
@Tag(name = "Autores")
@Slf4j
public class CargoController implements GenericController {

    private final CargoService cargoService;

    private final CargoMapper cargoMapper;

    @GetMapping
    @Operation(summary = "Listar", description = "Listar todos os cargos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso.")
    })
    public ResponseEntity<List<CargoDTO>> listar() {
        log.info("Listando todos os cargos");

        List<CargoDTO> resultado = cargoService.findAll()
                .stream()
                .map(cargoMapper::toDTO)
                .toList();

        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter Por ID", description = "Pesquisar Cargo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autor encontrado."),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado.")
    })
    public ResponseEntity<CargoDTO> getCargoById(@PathVariable Integer id) {

        CargoDTO cargoResultado = cargoMapper.toDTO(cargoService.findById(id));
        return ResponseEntity.ok(cargoResultado);
    }

    @PostMapping
    @Operation(summary = "Criar ", description = "Criar um Cargo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autor encontrado."),
    })
    public ResponseEntity<CargoDTO> createCargo(@RequestBody @Valid CargoDTO cargoDTO) {
        log.info("Cadastrando novo cargo: {}", cargoDTO.nomeCargo());

        Cargo cargo = cargoMapper.toEntity(cargoDTO);
        cargoService.save(cargo);
        URI location = gerarHeaderLocation(cargo.getIdCargo());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update ", description = "Update um Cargo")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cargo atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Cargo não encontrado."),
    })
    public ResponseEntity<Void> updateCargo(@PathVariable Integer id, @RequestBody @Valid CargoDTO cargoDTO) {
        Cargo cargo =  cargoMapper.toEntity(cargoDTO);
        cargo.setIdCargo(id);

        cargoService.update(cargo);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete ", description = "Deletar um Cargo")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cargo Deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Cargo não encontrado."),
            @ApiResponse(responseCode = "409", description = "Alguma entidade depende do cargo.")
    })
    public ResponseEntity<Void> deleteCargo(@PathVariable Integer id) {

        cargoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

