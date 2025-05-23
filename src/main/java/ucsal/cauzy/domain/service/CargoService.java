package ucsal.cauzy.domain.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucsal.cauzy.domain.entity.Cargo;
import ucsal.cauzy.domain.repository.CargoRepository;
import ucsal.cauzy.domain.utils.exception.NotFoundException;
import ucsal.cauzy.domain.utils.exception.ResourceNotFoundException;
import ucsal.cauzy.rest.dto.CargoDTO;
import ucsal.cauzy.rest.mapper.CargoMapper;
import ucsal.cauzy.rest.validator.CargoValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CargoService {

    private final CargoRepository cargoRepository;

    private final CargoMapper cargoMapper;

    private final CargoValidator cargoValidator;

    public List<Cargo> findAll() {
        return cargoRepository.findAll();
    }

    public Cargo findById(Integer id) {
        cargoValidator.existsCargo(id);
        return cargoRepository.findById(id).get();
    }

    public Cargo save(Cargo cargo) {
        return cargoRepository.save(cargo);
    }

    public Cargo update(Cargo cargo, Integer idCargo) {
        cargoValidator.existsCargo(idCargo);
        cargo.setIdCargo(idCargo);
        return cargoRepository.save(cargo);
    }

    public void delete(Integer id) {
        cargoValidator.existsCargo(id);
        cargoValidator.validateDependencies(id);
        cargoRepository.deleteById(id);
    }
}
