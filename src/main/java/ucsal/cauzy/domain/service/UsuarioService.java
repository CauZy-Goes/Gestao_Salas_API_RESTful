package ucsal.cauzy.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucsal.cauzy.domain.entity.Usuario;
import ucsal.cauzy.domain.repository.UsuarioRepository;
import ucsal.cauzy.domain.service.exceptions.EmailAlreadyExistsException;
import ucsal.cauzy.domain.service.exceptions.ResourceNotFoundException;
import ucsal.cauzy.rest.dto.UsuarioDTO;
import ucsal.cauzy.rest.mapper.UsuarioMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO findById(Integer id) {

        return usuarioRepository.findById(id)
                .map(usuarioMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public UsuarioDTO findByEmail(String email) {
        String emailFormatado = email.trim();
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(emailFormatado);
        return usuarioRepository.findByEmail(emailFormatado)
                .map(usuarioMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException(emailFormatado));
    }

    public void checkEmail(Usuario usuario, Integer id) {
        Optional<Usuario> emailAlreadyExists = usuarioRepository.findByEmail(usuario.getEmail());
        if (emailAlreadyExists.isPresent() && !emailAlreadyExists.get().getIdUsuario().equals(id)) {
            throw new EmailAlreadyExistsException();
        }
    }

    public UsuarioDTO save(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        checkEmail(usuario, usuario.getIdUsuario());
        Usuario savedUsuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(savedUsuario);
    }

    public UsuarioDTO update(Integer id, UsuarioDTO usuarioDTO) {
        if (usuarioRepository.existsById(id)) {
            Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
            checkEmail(usuario, id);
            usuario.setIdUsuario(id); // Garante que o ID não seja sobrescrito
            Usuario updatedUsuario = usuarioRepository.save(usuario);
            return usuarioMapper.toDTO(updatedUsuario);
        }
        throw new ResourceNotFoundException(id);
    }

    public void delete(Integer id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(id);
        }
    }
}
