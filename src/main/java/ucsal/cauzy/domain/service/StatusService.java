package ucsal.cauzy.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucsal.cauzy.domain.entity.Status;
import ucsal.cauzy.domain.repository.StatusRepository;
import ucsal.cauzy.domain.utils.exception.ResourceNotFoundException;
import ucsal.cauzy.rest.dto.StatusDTO;
import ucsal.cauzy.rest.mapper.StatusMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private StatusMapper statusMapper;

    public List<Status> findAll() {
        return statusRepository.findAll();
    }

    public Status findById(Integer id) {
        return statusRepository.findById(id).orElse(null);
    }

    public StatusDTO save(StatusDTO statusDTO) {
        Status status = statusMapper.toEntity(statusDTO);
        Status savedStatus = statusRepository.save(status);
        return statusMapper.toDTO(savedStatus);
    }

    public StatusDTO update(Integer id, StatusDTO statusDTO) {
        if (statusRepository.existsById(id)) {
            Status status = statusMapper.toEntity(statusDTO);
            status.setIdStatus(id); // Garante que o ID não seja sobrescrito
            Status updatedStatus = statusRepository.save(status);
            return statusMapper.toDTO(updatedStatus);
        }
        throw new ResourceNotFoundException("Status", id);
    }

    public void delete(Integer id) {
        if (!statusRepository.existsById(id)) {
            throw new ResourceNotFoundException("Status", id);
        }
        statusRepository.deleteById(id);
    }
}