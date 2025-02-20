package ucsal.cauzy.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ucsal.cauzy.domain.entity.EspacoFisico;
import ucsal.cauzy.rest.dto.EspacoFisicoDTO;

@Mapper(componentModel = "spring")
public interface EspacoFisicoMapper {

    @Mapping(source = "numero", target = "numero")
    @Mapping(source = "tipoSala.idTipoSala", target = "idTipoSala")
    EspacoFisicoDTO toDTO(EspacoFisico espacoFisico);

    @Mapping(source = "numero", target = "numero")
    @Mapping(source = "idTipoSala", target = "tipoSala.idTipoSala")
    EspacoFisico toEntity(EspacoFisicoDTO espacoFisicoDTO);
}
