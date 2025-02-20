package ucsal.cauzy.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ucsal.cauzy.domain.entity.TipoSala;
import ucsal.cauzy.rest.dto.TipoSalaDTO;

@Mapper(componentModel = "spring")
public interface TipoSalaMapper {

    TipoSalaDTO toDTO(TipoSala tipoSala);


    TipoSala toEntity(TipoSalaDTO tipoSalaDTO);
}
