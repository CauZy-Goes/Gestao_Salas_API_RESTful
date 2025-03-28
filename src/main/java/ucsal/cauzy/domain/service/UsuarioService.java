package ucsal.cauzy.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ucsal.cauzy.domain.entity.Cargo;
import ucsal.cauzy.domain.entity.Usuario;
import ucsal.cauzy.domain.repository.*;
import ucsal.cauzy.domain.utils.exceptions.EmailAlreadyExistsException;
import ucsal.cauzy.domain.utils.exceptions.ResourceNotFoundException;
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
    private LogAcoesRepository logAcoesRepository;

    @Autowired
    private EspacoFisicoRepository espacoFisicoRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    CargoRepository cargoRepository;
    @Autowired
    private SolicitacoesRepository solicitacoesRepository;

    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO findById(Integer id) {
        return usuarioRepository.findById(id)
                .map(usuarioMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", id));
    }


    public UsuarioDTO findByEmail(String email) {
        return Optional.ofNullable(email)
                .map(String::trim)
                .filter(e -> !e.isEmpty()) // Evita strings vazias
                .flatMap(usuarioRepository::findByEmail)
                .map(usuarioMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Email " ,email));
    }


    public void checkEmail(Usuario usuario, Integer id) {
        usuarioRepository.findByEmail(usuario.getEmail())
                .ifPresent(existingUsuario -> {
                    if (!existingUsuario.getIdUsuario().equals(id)) {
                        throw new EmailAlreadyExistsException();
                    }
                });
    }

    public UsuarioDTO save(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        checkEmail(usuario, null);

        Cargo cargo = usuarioDTO.idCargo() == null ? null :
                cargoRepository.findById(usuarioDTO.idCargo()).orElse(null);

        usuario.setCargo(cargo);
        Usuario savedUsuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(savedUsuario);
    }

    public UsuarioDTO update(Integer id, UsuarioDTO usuarioDTO) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário", id);
        }
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        checkEmail(usuario, id);
        usuario.setIdUsuario(id);
        Usuario updatedUsuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(updatedUsuario);
    }

    @Transactional
    public void delete(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário", id);
        }
        logAcoesRepository.deleteByUsuarioId(id);
        solicitacoesRepository.deleteByUsuarioId(id);
        usuarioRepository.deleteById(id);
    }
}
