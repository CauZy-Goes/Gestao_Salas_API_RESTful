package ucsal.cauzy.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucsal.cauzy.domain.entity.EspacoFisico;
import ucsal.cauzy.domain.entity.Usuario;
import ucsal.cauzy.domain.repository.EspacoFisicoRepository;
import ucsal.cauzy.domain.utils.exceptions.DuplicateResourceException;
import ucsal.cauzy.domain.utils.exceptions.EmailAlreadyExistsException;
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

    public EspacoFisicoDTO findByNumero(Integer numero) {
        return  espacoFisicoRepository.findByNumero(numero)
                                    .map(espacoFisicoMapper::toDTO)
                                    .orElseThrow(() -> new ResourceNotFoundException("Numero De Sala", numero));
    }

    public EspacoFisicoDTO save(EspacoFisicoDTO espacoFisicoDTO) {
        EspacoFisico espacoFisico = espacoFisicoMapper.toEntity(espacoFisicoDTO);
        checkNumero(espacoFisico, null);
        EspacoFisico savedEspacoFisico = espacoFisicoRepository.save(espacoFisico);
        return espacoFisicoMapper.toDTO(savedEspacoFisico);
    }

    public EspacoFisicoDTO update(Integer id, EspacoFisicoDTO espacoFisicoDTO) {
        if (!espacoFisicoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Espaço Físico", id);
        }
        EspacoFisico espacoFisico = espacoFisicoMapper.toEntity(espacoFisicoDTO);
        checkNumero(espacoFisico, id);
        espacoFisico.setIdEspacoFisico(id);
        EspacoFisico updatedEspacoFisico = espacoFisicoRepository.save(espacoFisico);
        return espacoFisicoMapper.toDTO(updatedEspacoFisico);
    }

    public void checkNumero(EspacoFisico sala, Integer id) {
        espacoFisicoRepository.findByNumero(sala.getNumero())
                .ifPresent(existingSala -> {
                    if (!existingSala.getIdEspacoFisico().equals(id)) {
                        throw new DuplicateResourceException("Esse numero da Sala", "numero", Integer.toString(existingSala.getNumero()));
                    }
                });
    }

    public void delete(Integer id) {
        if (!espacoFisicoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Espaço Físico", id);
        }
        espacoFisicoRepository.deleteById(id);
    }
}
