package ucsal.cauzy.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ucsal.cauzy.domain.entity.Cargo;
import ucsal.cauzy.domain.entity.Usuario;
import ucsal.cauzy.domain.repository.*;
import ucsal.cauzy.domain.utils.exception.EmailAlreadyExistsException;
import ucsal.cauzy.domain.utils.exception.ResourceNotFoundException;
import ucsal.cauzy.rest.dto.UsuarioDTO;
import ucsal.cauzy.rest.mapper.UsuarioMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;


    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }
}
