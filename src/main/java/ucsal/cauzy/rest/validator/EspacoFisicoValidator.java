package ucsal.cauzy.rest.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ucsal.cauzy.domain.entity.EspacoFisico;
import ucsal.cauzy.domain.entity.Usuario;
import ucsal.cauzy.domain.repository.EspacoFisicoRepository;
import ucsal.cauzy.domain.utils.exception.DuplicateResourceException;
import ucsal.cauzy.domain.utils.exception.NotFoundException;

@Component
@RequiredArgsConstructor
public class EspacoFisicoValidator {

    private final EspacoFisicoRepository espacoFisicoRepository;

    public void existsEspacoFisico(Integer id) {
        if (!espacoFisicoRepository.existsById(id)) {
            throw new NotFoundException("Espaco fisico não encotrado com o id : " + id);
        }
    }

    public void existsNumero(Integer numero){
        if(espacoFisicoRepository.findByNumero(numero).isPresent()){
            throw new DuplicateResourceException("Espaco Fisico não encotrando com o numero : " + numero);
        }
    }

    public void existsByIdAndNumero(Integer id, Integer numero){
        existsEspacoFisico(id);

        EspacoFisico espacoFisico = espacoFisicoRepository.findById(id).get();
        if(espacoFisico.getNumero().equals(numero)){
            return;
        }

        existsNumero(numero);
    }
}
