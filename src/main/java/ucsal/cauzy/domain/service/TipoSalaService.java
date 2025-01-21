package ucsal.cauzy.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucsal.cauzy.domain.entity.TipoSala;
import ucsal.cauzy.domain.repository.TipoSalaRepository;
import ucsal.cauzy.rest.dto.TipoSalaDTO;
import ucsal.cauzy.rest.mapper.TipoSalaMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TipoSalaService {

    @Autowired
    private TipoSalaRepository tipoSalaRepository;

    @Autowired
    private TipoSalaMapper tipoSalaMapper;

    public List<TipoSalaDTO> findAll() {
        return tipoSalaRepository.findAll()
                .stream()
                .map(tipoSalaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<TipoSalaDTO> findById(Integer id) {
        return tipoSalaRepository.findById(id)
                .map(tipoSalaMapper::toDTO);
    }

    public TipoSalaDTO save(TipoSalaDTO tipoSalaDTO) {
        TipoSala tipoSala = tipoSalaMapper.toEntity(tipoSalaDTO);
        TipoSala savedTipoSala = tipoSalaRepository.save(tipoSala);
        return tipoSalaMapper.toDTO(savedTipoSala);
    }

    public TipoSalaDTO update(Integer id, TipoSalaDTO tipoSalaDTO) {
        if (tipoSalaRepository.existsById(id)) {
            TipoSala tipoSala = tipoSalaMapper.toEntity(tipoSalaDTO);
            tipoSala.setIdTipoSala(id); // Garante que o ID não seja sobrescrito
            TipoSala updatedTipoSala = tipoSalaRepository.save(tipoSala);
            return tipoSalaMapper.toDTO(updatedTipoSala);
        }
        throw new IllegalArgumentException("TipoSala não encontrado com o ID: " + id);
    }

    public void delete(Integer id) {
        if (tipoSalaRepository.existsById(id)) {
            tipoSalaRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("TipoSala não encontrado com o ID: " + id);
        }
    }
}
