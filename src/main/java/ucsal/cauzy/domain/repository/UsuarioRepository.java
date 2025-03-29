package ucsal.cauzy.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import ucsal.cauzy.domain.entity.Cargo;
import ucsal.cauzy.domain.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Busca um usuário pelo email
    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByCargo(Cargo cargo);
}
