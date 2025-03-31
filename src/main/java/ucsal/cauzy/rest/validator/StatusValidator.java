package ucsal.cauzy.rest.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ucsal.cauzy.domain.entity.Status;
import ucsal.cauzy.domain.repository.SolicitacoesRepository;
import ucsal.cauzy.domain.repository.StatusRepository;
import ucsal.cauzy.domain.utils.exception.NotFoundException;
import ucsal.cauzy.domain.utils.exception.ResourceInUseException;

@Component
@RequiredArgsConstructor
public class StatusValidator {

    private final StatusRepository statusRepository;

    private final SolicitacoesRepository solicitacoesRepository;

    public void existsStatus(Integer id) {
        if(!statusRepository.existsById(id)) {
            throw new NotFoundException("Status não encontrado com o id : " + id);
        }
    }

    public void validateDependencies(Integer id){
        existsStatus(id);
        Status status = statusRepository.findById(id).get();

        if(!solicitacoesRepository.findByStatus(status).isEmpty()){
            throw new ResourceInUseException("Alguma solicitacao depende desse status");
        }
    }
}
