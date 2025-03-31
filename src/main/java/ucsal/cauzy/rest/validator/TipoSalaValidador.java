package ucsal.cauzy.rest.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ucsal.cauzy.domain.entity.TipoSala;
import ucsal.cauzy.domain.repository.EspacoFisicoRepository;
import ucsal.cauzy.domain.repository.TipoSalaRepository;
import ucsal.cauzy.domain.utils.exception.NotFoundException;
import ucsal.cauzy.domain.utils.exception.ResourceInUseException;

@Component
@RequiredArgsConstructor
public class TipoSalaValidador {

    private final TipoSalaRepository tipoSalaRepository;

    private final EspacoFisicoRepository espacoFisicoRepository;

    public void existsTipoSala(Integer id){
        if(!tipoSalaRepository.existsById(id)){
            throw new NotFoundException("Tipo sala não encontrado com o id : " + id);
        }
    }

    public void validateDependencies(Integer id){
        existsTipoSala(id);
        TipoSala tipoSala = tipoSalaRepository.findById(id).get();

        if(!espacoFisicoRepository.findByTipoSala(tipoSala).isEmpty()){
            throw new ResourceInUseException("Algum espaco fisico depende desse tipo sala");
        }
    }
}
