package ucsal.cauzy.rest.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucsal.cauzy.domain.entity.TipoSala;
import ucsal.cauzy.domain.service.TipoSalaService;
import ucsal.cauzy.rest.dto.TipoSalaDTO;
import ucsal.cauzy.rest.mapper.TipoSalaMapper;

import java.util.List;

@RestController
@RequestMapping("tipoSala")
@RequiredArgsConstructor
@Tag(name="TipoSala")
@Slf4j
public class tipoSalaController implements GenericController{

    private final TipoSalaService tipoSalaService;

    private final TipoSalaMapper tipoSalaMapper;

}

