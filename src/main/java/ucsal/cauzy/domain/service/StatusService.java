package ucsal.cauzy.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucsal.cauzy.domain.entity.Status;
import ucsal.cauzy.domain.repository.StatusRepository;
import ucsal.cauzy.domain.utils.exception.ResourceNotFoundException;
import ucsal.cauzy.rest.dto.StatusDTO;
import ucsal.cauzy.rest.mapper.StatusMapper;
import ucsal.cauzy.rest.validator.StatusValidator;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatusService {

    private final StatusRepository statusRepository;

    private final StatusValidator statusValidator;

    public List<Status> findAll() {
        return statusRepository.findAll();
    }

    public Status findById(Integer id) {
        statusValidator.existsStatus(id);
        return statusRepository.findById(id).orElse(null);
    }

    public Status save(Status status) {
        return statusRepository.save(status);
    }

    public Status update(Integer id, Status status) {
        statusValidator.existsStatus(id);
        status.setIdStatus(id);
        return statusRepository.save(status);
    }

    public void delete(Integer id) {
        if (!statusRepository.existsById(id)) {
            throw new ResourceNotFoundException("Status", id);
        }
        statusRepository.deleteById(id);
    }
}