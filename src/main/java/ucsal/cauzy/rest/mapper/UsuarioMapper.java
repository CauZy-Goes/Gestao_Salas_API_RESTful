package ucsal.cauzy.rest.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ucsal.cauzy.domain.entity.Usuario;
import ucsal.cauzy.domain.service.CargoService;
import ucsal.cauzy.rest.dto.UsuarioDTO;
import ucsal.cauzy.rest.dto.UsuarioPesquisaDTO;


@Mapper(componentModel = "spring", uses = {CargoMapper.class})
public abstract class UsuarioMapper {

    @Autowired
    CargoService cargoService;

    public abstract UsuarioPesquisaDTO toDTO(Usuario usuario);

    @Mapping(target = "cargo", expression = "java(cargoService.findById(usuarioDTO.idCargo()))")
    public abstract Usuario toEntity(UsuarioDTO usuarioDTO);
}
