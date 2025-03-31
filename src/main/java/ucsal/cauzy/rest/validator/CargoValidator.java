package ucsal.cauzy.rest.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ucsal.cauzy.domain.entity.Cargo;
import ucsal.cauzy.domain.repository.CargoRepository;
import ucsal.cauzy.domain.repository.UsuarioRepository;
import ucsal.cauzy.domain.utils.exception.NotFoundException;
import ucsal.cauzy.domain.utils.exception.ResourceInUseException;

@Component
@RequiredArgsConstructor
public class CargoValidator {

    private final CargoRepository cargoRepository;

    private final UsuarioRepository usuarioRepository;

    public void existsCargo (Integer id){
        if(cargoRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Cargo não encontrado com o id " + id);
        }
    }

    public void validateDependencies (Integer id) {
        existsCargo(id);
        Cargo cargo = cargoRepository.findById(id).get();
        if (!usuarioRepository.findByCargo(cargo).isEmpty()) {
            throw new ResourceInUseException("Algum usuário depende desse cargo");
        };
    }
}
