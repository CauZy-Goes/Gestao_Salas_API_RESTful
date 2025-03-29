package ucsal.cauzy.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucsal.cauzy.domain.entity.LogAcoes;
import ucsal.cauzy.domain.repository.LogAcoesRepository;
import ucsal.cauzy.domain.utils.exception.ResourceNotFoundException;
import ucsal.cauzy.rest.dto.LogAcoesDTO;
import ucsal.cauzy.rest.mapper.LogAcoesMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogAcoesService {

    @Autowired
    private LogAcoesRepository logAcoesRepository;

    @Autowired
    private LogAcoesMapper logAcoesMapper;

    public List<LogAcoesDTO> findAll() {
        return logAcoesRepository.findAll()
                .stream()
                .map(logAcoesMapper::toDTO)
                .collect(Collectors.toList());
    }

    public LogAcoesDTO findById(Integer id) {
        return logAcoesRepository.findById(id)
                .map(logAcoesMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Log de Ações", id));
    }

    public LogAcoesDTO save(LogAcoesDTO logAcoesDTO) {
        LogAcoes logAcoes = logAcoesMapper.toEntity(logAcoesDTO);
        LogAcoes savedLogAcoes = logAcoesRepository.save(logAcoes);
        return logAcoesMapper.toDTO(savedLogAcoes);
    }

    public LogAcoesDTO update(Integer id, LogAcoesDTO logAcoesDTO) {
        if (!logAcoesRepository.existsById(id)) {
            throw new ResourceNotFoundException("Log de Ações", id);
        }
        LogAcoes logAcoes = logAcoesMapper.toEntity(logAcoesDTO);
        logAcoes.setIdLogAcoes(id); // Garante que o ID não seja sobrescrito
        LogAcoes updatedLogAcoes = logAcoesRepository.save(logAcoes);
        return logAcoesMapper.toDTO(updatedLogAcoes);
    }

    public void delete(Integer id) {
        if (!logAcoesRepository.existsById(id)) {
            throw new ResourceNotFoundException("Log de Ações", id);
        }
        logAcoesRepository.deleteById(id);
    }
}
