package ucsal.cauzy.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ucsal.cauzy.domain.entity.Solicitacoes;
import ucsal.cauzy.rest.dto.SolicitacoesDTO;

@Mapper(componentModel = "spring")
public interface SolicitacoesMapper {


    @Mapping(source = "descricao", target = "descricao")
    @Mapping(source = "usuarioAvaliador.idUsuario", target = "idUsuarioAvaliador")
    @Mapping(source = "usuarioSolicitante.idUsuario", target = "idUsuarioSolicitante")
    @Mapping(source = "espacoFisico.idEspacoFisico", target = "idEspacoFisico")
    @Mapping(source = "status.idStatus", target = "idStatus")
    SolicitacoesDTO toDTO(Solicitacoes solicitacoes);

    @Mapping(source = "descricao", target = "descricao")
    @Mapping(source = "idUsuarioAvaliador", target = "usuarioAvaliador.idUsuario")
    @Mapping(source = "idUsuarioSolicitante", target = "usuarioSolicitante.idUsuario")
    @Mapping(source = "idEspacoFisico", target = "espacoFisico.idEspacoFisico")
    @Mapping(source = "idStatus", target = "status.idStatus")
    Solicitacoes toEntity(SolicitacoesDTO solicitacoesDTO);
}
