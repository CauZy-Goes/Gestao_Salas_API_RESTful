package ucsal.cauzy.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucsal.cauzy.domain.entity.Cargo;
import ucsal.cauzy.domain.repository.CargoRepository;
import ucsal.cauzy.domain.utils.exception.NotFoundException;
import ucsal.cauzy.domain.utils.exception.ResourceNotFoundException;
import ucsal.cauzy.rest.dto.CargoDTO;
import ucsal.cauzy.rest.mapper.CargoMapper;

import java.util.List;

@Service
public class CargoService {

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private CargoMapper cargoMapper;

    public List<Cargo> findAll() {
        return cargoRepository.findAll();
    }

    public Cargo findById(Integer id) {
        return cargoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cargo não encontrado com o id " + id));
    }

    public Cargo save(Cargo cargo) {
        return cargoRepository.save(cargo);
    }

    public CargoDTO update(Integer id, CargoDTO cargoDTO) {
        if (!cargoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cargo", id);
        }
        Cargo cargo = cargoMapper.toEntity(cargoDTO);
        cargo.setIdCargo(id); // Garante que o ID não seja sobrescrito
        Cargo updatedCargo = cargoRepository.save(cargo);
        return cargoMapper.toDTO(updatedCargo);
    }

    public void delete(Integer id) {
        if (!cargoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cargo", id);
        }
        cargoRepository.deleteById(id);
    }
}
