package ucsal.cauzy.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucsal.cauzy.domain.entity.EspacoFisico;
import ucsal.cauzy.domain.repository.EspacoFisicoRepository;
import ucsal.cauzy.domain.utils.exceptions.ResourceNotFoundException;
import ucsal.cauzy.rest.dto.EspacoFisicoDTO;
import ucsal.cauzy.rest.mapper.EspacoFisicoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EspacoFisicoService {

    @Autowired
    private EspacoFisicoRepository espacoFisicoRepository;

    @Autowired
    private EspacoFisicoMapper espacoFisicoMapper;

    public List<EspacoFisicoDTO> findAll() {
        return espacoFisicoRepository.findAll()
                .stream()
                .map(espacoFisicoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public EspacoFisicoDTO findById(Integer id) {
        return espacoFisicoRepository.findById(id)
                .map(espacoFisicoMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Espaço Físico", id));
    }

    public EspacoFisicoDTO save(EspacoFisicoDTO espacoFisicoDTO) {
        EspacoFisico espacoFisico = espacoFisicoMapper.toEntity(espacoFisicoDTO);
        EspacoFisico savedEspacoFisico = espacoFisicoRepository.save(espacoFisico);
        return espacoFisicoMapper.toDTO(savedEspacoFisico);
    }

    public EspacoFisicoDTO update(Integer id, EspacoFisicoDTO espacoFisicoDTO) {
        if (!espacoFisicoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Espaço Físico", id);
        }
        EspacoFisico espacoFisico = espacoFisicoMapper.toEntity(espacoFisicoDTO);
        espacoFisico.setIdEspacoFisico(id);
        EspacoFisico updatedEspacoFisico = espacoFisicoRepository.save(espacoFisico);
        return espacoFisicoMapper.toDTO(updatedEspacoFisico);
    }

    public void delete(Integer id) {
        if (!espacoFisicoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Espaço Físico", id);
        }
        espacoFisicoRepository.deleteById(id);
    }
}
