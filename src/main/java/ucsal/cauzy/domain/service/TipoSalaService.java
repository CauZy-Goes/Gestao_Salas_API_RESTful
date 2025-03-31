package ucsal.cauzy.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucsal.cauzy.domain.entity.TipoSala;
import ucsal.cauzy.domain.repository.TipoSalaRepository;
import ucsal.cauzy.domain.utils.exception.ResourceNotFoundException;
import ucsal.cauzy.rest.dto.TipoSalaDTO;
import ucsal.cauzy.rest.mapper.TipoSalaMapper;
import ucsal.cauzy.rest.validator.TipoSalaValidador;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TipoSalaService {

    private final TipoSalaRepository tipoSalaRepository;

    private final TipoSalaValidador tipoSalaValidador;

    public TipoSala findById(Integer id) {
        tipoSalaValidador.existsTipoSala(id);
        return tipoSalaRepository.findById(id).get();
    }

    public List<TipoSala> findAll() {
        return tipoSalaRepository.findAll();
    }

    public TipoSala save(TipoSala tipoSala) {
        return tipoSalaRepository.save(tipoSala);
    }

    public TipoSala update(TipoSala tipoSala, Integer id){
        tipoSalaValidador.existsTipoSala(id);
        tipoSalaValidador.validateDependencies(id);
        return tipoSalaRepository.save(tipoSala);
    }

    public void delete(Integer id) {
        tipoSalaValidador.existsTipoSala(id);
        tipoSalaValidador.validateDependencies(id);
        tipoSalaRepository.deleteById(id);
    }
}
