package ucsal.cauzy.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ucsal.cauzy.domain.entity.EspacoFisico;
import ucsal.cauzy.domain.service.TipoSalaService;
import ucsal.cauzy.rest.dto.EspacoFisicoDTO;
import ucsal.cauzy.rest.dto.EspacoFisicoPesquisaDTO;

@Mapper(componentModel = "spring", uses = TipoSalaMapper.class)
public abstract class EspacoFisicoMapper {

    @Autowired
    TipoSalaService tipoSalaService;

    public abstract EspacoFisicoPesquisaDTO toDTO(EspacoFisico espacoFisico);

    @Mapping(target = "tipoSala", expression = "java(tipoSalaService.findById(espacoFisicoDTO.idTipoSala()))" )
    public abstract EspacoFisico toEntity(EspacoFisicoDTO espacoFisicoDTO);
}
