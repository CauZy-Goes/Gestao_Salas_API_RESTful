package ucsal.cauzy.rest.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucsal.cauzy.domain.service.EspacoFisicoService;
import ucsal.cauzy.rest.dto.EspacoFisicoDTO;
import ucsal.cauzy.rest.mapper.EspacoFisicoMapper;

import java.util.List;

@RestController
@RequestMapping("espacoFisico")
@Slf4j
@RequiredArgsConstructor
@Tag(name="Espaco Físico")
public class EspacoFisicoController implements GenericController{

    private final EspacoFisicoService espacoFisicoService;

    private final EspacoFisicoMapper espacoFisicoMapper;
}

