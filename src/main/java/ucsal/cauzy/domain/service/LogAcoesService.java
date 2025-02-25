package ucsal.cauzy.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucsal.cauzy.domain.entity.LogAcoes;
import ucsal.cauzy.domain.repository.LogAcoesRepository;
import ucsal.cauzy.domain.utils.exceptions.ResourceNotFoundException;
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
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public LogAcoesDTO save(LogAcoesDTO logAcoesDTO) {
        LogAcoes logAcoes = logAcoesMapper.toEntity(logAcoesDTO);
        LogAcoes savedLogAcoes = logAcoesRepository.save(logAcoes);
        return logAcoesMapper.toDTO(savedLogAcoes);
    }

    public LogAcoesDTO update(Integer id, LogAcoesDTO logAcoesDTO) {
        if (logAcoesRepository.existsById(id)) {
            LogAcoes logAcoes = logAcoesMapper.toEntity(logAcoesDTO);
            logAcoes.setIdLogAcoes(id); // Garante que o ID não seja sobrescrito
            LogAcoes updatedLogAcoes = logAcoesRepository.save(logAcoes);
            return logAcoesMapper.toDTO(updatedLogAcoes);
        }
        throw new ResourceNotFoundException(id);
    }

    public void delete(Integer id) {
        if (logAcoesRepository.existsById(id)) {
            logAcoesRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(id);
        }
    }
}
