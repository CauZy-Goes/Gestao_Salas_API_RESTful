package ucsal.cauzy.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ucsal.cauzy.domain.entity.EspacoFisico;
import ucsal.cauzy.domain.repository.EspacoFisicoRepository;
import ucsal.cauzy.domain.repository.SolicitacoesRepository;
import ucsal.cauzy.rest.validator.EspacoFisicoValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EspacoFisicoService {

    private final EspacoFisicoValidator espacoFisicoValidator;

    private final EspacoFisicoRepository espacoFisicoRepository;

    private final SolicitacoesRepository solicitacoesRepository;

    public EspacoFisico findById(Integer id) {
        espacoFisicoValidator.existsEspacoFisico(id);
        return espacoFisicoRepository.findById(id).get();
    }

    public List<EspacoFisico> findAll (){
        return espacoFisicoRepository.findAll();
    }

    public EspacoFisico save(EspacoFisico espacoFisico) {
        espacoFisicoValidator.existsNumero(espacoFisico.getNumero());
        return espacoFisicoRepository.save(espacoFisico);
    }

    public void update(EspacoFisico espacoFisico, Integer id) {
        espacoFisicoValidator.existsByIdAndNumero(id , espacoFisico.getNumero());
        espacoFisico.setIdEspacoFisico(id);
        espacoFisicoRepository.save(espacoFisico);
    }

    public void delete(Integer id) {
        espacoFisicoValidator.existsEspacoFisico(id);
        EspacoFisico espacoFisico = espacoFisicoRepository.findById(id).get();
        if(!solicitacoesRepository.findByEspacoFisico(espacoFisico).isEmpty()){
            solicitacoesRepository.deleteByEspacoFisicoId(id);
        }
        espacoFisicoRepository.delete(espacoFisico);
    }
}
