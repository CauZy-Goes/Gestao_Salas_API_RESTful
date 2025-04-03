package ucsal.cauzy.rest.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucsal.cauzy.domain.entity.Solicitacoes;
import ucsal.cauzy.domain.service.SolicitacoesService;
import ucsal.cauzy.rest.dto.SolicitacoesDTO;
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
}

