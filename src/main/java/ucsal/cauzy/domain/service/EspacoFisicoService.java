package ucsal.cauzy.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ucsal.cauzy.domain.entity.EspacoFisico;
import ucsal.cauzy.domain.entity.TipoSala;
import ucsal.cauzy.domain.repository.EspacoFisicoRepository;
import ucsal.cauzy.domain.repository.SolicitacoesRepository;
import ucsal.cauzy.domain.repository.TipoSalaRepository;
import ucsal.cauzy.domain.utils.exception.DuplicateResourceException;
import ucsal.cauzy.domain.utils.exception.ResourceNotFoundException;
import ucsal.cauzy.rest.dto.EspacoFisicoDTO;
import ucsal.cauzy.rest.mapper.EspacoFisicoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EspacoFisicoService {

    @Autowired
    private EspacoFisicoRepository espacoFisicoRepository;

    @Autowired
    private SolicitacoesRepository solicitacoesRepository;

    @Autowired
    private EspacoFisicoMapper espacoFisicoMapper;

    @Autowired
    private TipoSalaRepository tipoSalaRepository;

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

        TipoSala tipoSala = espacoFisicoDTO.idTipoSala() == null ? null :
                tipoSalaRepository.findById(espacoFisicoDTO.idTipoSala()).orElse(null);

        espacoFisico.setTipoSala(tipoSala);

        EspacoFisico savedEspacoFisico = espacoFisicoRepository.save(espacoFisico);
        return espacoFisicoMapper.toDTO(savedEspacoFisico);
    }

    public EspacoFisicoDTO update(Integer id, EspacoFisicoDTO espacoFisicoDTO) {
        if (!espacoFisicoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Espaço Físico", id);
        }
        EspacoFisico espacoFisico = espacoFisicoMapper.toEntity(espacoFisicoDTO);
        checkNumero(espacoFisico, id);

        TipoSala tipoSala = espacoFisicoDTO.idTipoSala() == null ? null :
                tipoSalaRepository.findById(espacoFisicoDTO.idTipoSala()).orElse(null);

        espacoFisico.setTipoSala(tipoSala);
        espacoFisico.setIdEspacoFisico(id);

        EspacoFisico updatedEspacoFisico = espacoFisicoRepository.save(espacoFisico);
        return espacoFisicoMapper.toDTO(updatedEspacoFisico);
    }

    public void checkNumero(EspacoFisico sala, Integer id) {
        espacoFisicoRepository.findByNumero(sala.getNumero())
                .ifPresent(existingSala -> {
                    if (!existingSala.getIdEspacoFisico().equals(id)) {
                        throw new DuplicateResourceException("e");
                    }
                });
    }

    @Transactional
    public void delete(Integer id) {
        if (!espacoFisicoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Espaço Físico", id);
        }

        solicitacoesRepository.deleteByEspacoFisicoId(id);
        espacoFisicoRepository.deleteById(id);
    }
}
