package ucsal.cauzy.rest.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ucsal.cauzy.domain.entity.Usuario;
import ucsal.cauzy.domain.repository.CargoRepository;
import ucsal.cauzy.domain.service.CargoService;
import ucsal.cauzy.rest.dto.UsuarioDTO;
import ucsal.cauzy.rest.dto.UsuarioPesquisaDTO;

//uses = {CargoRepository.class
@Mapper(componentModel = "spring", uses = CargoMapper.class )
public interface UsuarioMapper {

    @Autowired
    CargoService cargoService = null;

    UsuarioPesquisaDTO toDTO(Usuario usuario);

    @Mapping(target = "cargo", expression = "cargoService.findById(usuarioDTO.idCargo)")
    Usuario toEntity(UsuarioDTO usuarioDTO);
}
