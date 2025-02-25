package ucsal.cauzy.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucsal.cauzy.domain.entity.Cargo;
import ucsal.cauzy.domain.repository.CargoRepository;
import ucsal.cauzy.domain.utils.exceptions.ResourceNotFoundException;
import ucsal.cauzy.rest.dto.CargoDTO;
import ucsal.cauzy.rest.mapper.CargoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CargoService {

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private CargoMapper cargoMapper;

    public List<CargoDTO> findAll() {
        return cargoRepository.findAll()
                .stream()
                .map(cargoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CargoDTO findById(Integer id) {
        return cargoRepository.findById(id)
                .map(cargoMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public CargoDTO save(CargoDTO cargoDTO) {
        Cargo cargo = cargoMapper.toEntity(cargoDTO);
        Cargo savedCargo = cargoRepository.save(cargo);
        return cargoMapper.toDTO(savedCargo);
    }

    public CargoDTO update(Integer id, CargoDTO cargoDTO) {
        if (cargoRepository.existsById(id)) {
            Cargo cargo = cargoMapper.toEntity(cargoDTO);
            cargo.setIdCargo(id); // Garante que o ID não seja sobrescrito
            Cargo updatedCargo = cargoRepository.save(cargo);
            return cargoMapper.toDTO(updatedCargo);
        }
        throw new ResourceNotFoundException(id);
    }

    public void delete(Integer id) {
        if (cargoRepository.existsById(id)) {
            cargoRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(id);
        }
    }
}
