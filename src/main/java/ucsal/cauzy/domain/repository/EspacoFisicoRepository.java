package ucsal.cauzy.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ucsal.cauzy.domain.entity.EspacoFisico;

public interface EspacoFisicoRepository extends JpaRepository<EspacoFisico, Integer> {
    EspacoFisico findByNumero(Integer numero);

}

