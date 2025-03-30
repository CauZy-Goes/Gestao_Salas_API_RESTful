package ucsal.cauzy.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ucsal.cauzy.domain.entity.Solicitacoes;
import ucsal.cauzy.domain.repository.UsuarioRepository;
import ucsal.cauzy.domain.service.EspacoFisicoService;
import ucsal.cauzy.domain.service.StatusService;
import ucsal.cauzy.domain.service.UsuarioService;
import ucsal.cauzy.rest.dto.SolicitacoesDTO;
import ucsal.cauzy.rest.dto.SolicitacoesPesquisaDTO;

@Mapper(componentModel = "spring", uses = {StatusService.class, UsuarioService.class})
public abstract class SolicitacoesMapper {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    StatusService statusService;

    @Autowired
    EspacoFisicoService espacoFisicoService;

    public abstract SolicitacoesPesquisaDTO toDTO(Solicitacoes solicitacoes);

    @Mapping(target = "usuarioAvaliador", expression = "java(usuarioService.findById(solicitacaoDTO.idUsuarioAvaliador()))")
    @Mapping(target = "usuarioSolicitante" , expression = "java(usuarioService.findById(solicitacaoDTO.idUsuarioSolicitante()))")
    @Mapping(target = "espacoFisico", expression = "java(espacoFisicoService.findById(solicitacaoDTO.idEspacoFisico()))")
    @Mapping(target = "status", expression = "java(statusService.findById(solicitacaoDTO.idStatus()))")
    public abstract Solicitacoes toEntity(SolicitacoesDTO solicitacaoDTO);
}
