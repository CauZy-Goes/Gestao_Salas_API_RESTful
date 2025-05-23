package ucsal.cauzy.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import ucsal.cauzy.domain.entity.Cargo;
import ucsal.cauzy.domain.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Busca um usuário pelo email
    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByNumero(String numero);

    List<Usuario> findByCargo(Cargo cargo);
}
