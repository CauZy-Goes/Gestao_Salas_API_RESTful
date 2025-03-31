package ucsal.cauzy.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ucsal.cauzy.domain.entity.EspacoFisico;
import ucsal.cauzy.domain.entity.TipoSala;
import ucsal.cauzy.domain.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface EspacoFisicoRepository extends JpaRepository<EspacoFisico, Integer> {

    Optional<EspacoFisico> findByNumero(Integer numero);

    List<EspacoFisico> findByTipoSala(TipoSala TipoSala);
}

