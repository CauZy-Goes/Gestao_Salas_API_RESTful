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
                .orElseThrow(() -> new ResourceNotFoundException("Cargo", id));
    }

    public CargoDTO save(CargoDTO cargoDTO) {
        Cargo cargo = cargoMapper.toEntity(cargoDTO);
        Cargo savedCargo = cargoRepository.save(cargo);
        return cargoMapper.toDTO(savedCargo);
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
