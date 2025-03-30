package ucsal.cauzy.rest.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ucsal.cauzy.domain.entity.Solicitacoes;
import ucsal.cauzy.domain.repository.SolicitacoesRepository;
import ucsal.cauzy.domain.utils.exception.NotFoundException;

@Component
@RequiredArgsConstructor
public class SolicitacoesValidator {

    private final SolicitacoesRepository solicitacoesRepository;

    public void existsSolicitacao(Integer id) {
        if (!solicitacoesRepository.existsById(id)){
            throw new NotFoundException("Solicitacao não encontrada com o ID: " + id);
        }
    }

}
