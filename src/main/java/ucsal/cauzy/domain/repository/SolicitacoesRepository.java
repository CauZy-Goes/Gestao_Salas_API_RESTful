package ucsal.cauzy.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ucsal.cauzy.domain.entity.Solicitacoes;

import java.util.List;

public interface SolicitacoesRepository extends JpaRepository<Solicitacoes, Integer> {

    // Buscar todas as solicitações feitas por um professor específico
     List<Solicitacoes> findByUsuarioSolicitante_IdUsuario(Integer idUsuarioSolicitante);

    @Modifying
    @Query("DELETE FROM Solicitacoes s WHERE s.espacoFisico.idEspacoFisico = :idEspaco")
    void deleteByEspacoFisicoId(@Param("idEspaco") Integer idEspaco);
}

