package ucsal.cauzy.rest.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ucsal.cauzy.domain.entity.Usuario;
import ucsal.cauzy.domain.repository.UsuarioRepository;
import ucsal.cauzy.domain.utils.exception.EmailAlreadyExistsException;
import ucsal.cauzy.domain.utils.exception.NotFoundException;

@Component
@RequiredArgsConstructor
public class UsuarioValidator {

    private final UsuarioRepository usuarioRepository;

    public void existsUsuario(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new NotFoundException("Usuario não encontrado com o id " + id);
        }
    }

    public void existsEmail(String email){
            if(usuarioRepository.findByEmail(email).isPresent()){
                throw new EmailAlreadyExistsException();
        }
    }

    public void existsByIdAndEmail(Integer id, String email){
        existsUsuario(id);

        Usuario usuario = usuarioRepository.findById(id).get();
        if(usuario.getEmail().equals(email)){
           return;
        }

        existsEmail(email);
    }
}
