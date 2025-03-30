package ucsal.cauzy.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ucsal.cauzy.domain.entity.EspacoFisico;
import ucsal.cauzy.domain.repository.EspacoFisicoRepository;
import ucsal.cauzy.rest.validator.EspacoFisicoValidator;

@Service
@RequiredArgsConstructor
public class EspacoFisicoService {

    protected final EspacoFisicoValidator espacoFisicoValidator;

    private final EspacoFisicoRepository espacoFisicoRepository;

    public EspacoFisico findById(Integer id) {
        espacoFisicoValidator.existsEspacoFisico(id);
        return espacoFisicoRepository.findById(id).get();
    }

    public void findAll (){

    }

    public void save(EspacoFisico espacoFisico) {

    }


    public void update(EspacoFisico espacoFisico) {

    }

    public void delete(Long id) {

    }
}
