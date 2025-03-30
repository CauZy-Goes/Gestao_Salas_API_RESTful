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
import ucsal.cauzy.rest.validator.UsuarioValidator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final UsuarioValidator usuarioValidator;

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    public Usuario findById(Integer id){
        usuarioValidator.existsUsuario(id);
        return usuarioRepository.findById(id).get();
    }

    public Usuario save(Usuario usuario){
        usuarioValidator.existsEmail(usuario.getEmail());
        return usuarioRepository.save(usuario);
    }

    public Usuario update(Usuario usuario, Integer id){
        usuarioValidator.existsByIdAndEmail(id, usuario.getEmail());
        return usuarioRepository.save(usuario);
    }
}
