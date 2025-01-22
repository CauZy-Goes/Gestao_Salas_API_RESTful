package ucsal.cauzy.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucsal.cauzy.domain.entity.Equipamento;
import ucsal.cauzy.domain.repository.EquipamentoRepository;
import ucsal.cauzy.domain.service.exceptions.ResourceNotFoundException;
import ucsal.cauzy.rest.dto.EquipamentoDTO;
import ucsal.cauzy.rest.mapper.EquipamentoMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EquipamentoService {

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    @Autowired
    private EquipamentoMapper equipamentoMapper;

    public List<EquipamentoDTO> findAll() {
        return equipamentoRepository.findAll()
                .stream()
                .map(equipamentoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public EquipamentoDTO findById(Integer id) {
        return equipamentoRepository.findById(id)
                .map(equipamentoMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public EquipamentoDTO save(EquipamentoDTO equipamentoDTO) {
        Equipamento equipamento = equipamentoMapper.toEntity(equipamentoDTO);
        Equipamento savedEquipamento = equipamentoRepository.save(equipamento);
        return equipamentoMapper.toDTO(savedEquipamento);
    }

    public EquipamentoDTO update(Integer id, EquipamentoDTO equipamentoDTO) {
        if (equipamentoRepository.existsById(id)) {
            Equipamento equipamento = equipamentoMapper.toEntity(equipamentoDTO);
            equipamento.setIdEquipamento(id); // Garante que o ID não seja sobrescrito
            Equipamento updatedEquipamento = equipamentoRepository.save(equipamento);
            return equipamentoMapper.toDTO(updatedEquipamento);
        }
        throw new ResourceNotFoundException(id);
    }

    public void delete(Integer id) {
        if (equipamentoRepository.existsById(id)) {
            equipamentoRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(id);
        }
    }
}
