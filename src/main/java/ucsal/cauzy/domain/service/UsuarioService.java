package ucsal.cauzy.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ucsal.cauzy.domain.entity.Cargo;
import ucsal.cauzy.domain.entity.Solicitacoes;
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

    private final SolicitacoesRepository solicitacoesRepository;

    private final UsuarioValidator usuarioValidator;

    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }

    public Usuario findByNumero(String numero) {
        return usuarioRepository.findByNumero(numero).orElse(null);
    }

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
        usuario.setIdUsuario(id);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void delete(Integer id){
        usuarioValidator.existsUsuario(id);
        if(!solicitacoesRepository.findByUsuarioId(id).isEmpty()){
            solicitacoesRepository.deleteByUsuarioId(id);
        }
        usuarioRepository.deleteById(id);
    }
}
