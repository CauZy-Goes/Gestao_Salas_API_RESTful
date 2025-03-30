package ucsal.cauzy.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ucsal.cauzy.domain.entity.Solicitacoes;
import ucsal.cauzy.domain.entity.Status;

import java.util.List;
import java.util.Optional;

public interface SolicitacoesRepository extends JpaRepository<Solicitacoes, Integer> {

    // Buscar todas as solicitações feitas por um professor específico
     List<Solicitacoes> findByUsuarioSolicitante_IdUsuario(Integer idUsuarioSolicitante);

     Optional<Solicitacoes> findByStatus(Status status);

    @Modifying
    @Query("DELETE FROM Solicitacoes s WHERE s.espacoFisico.idEspacoFisico = :idEspaco")
    void deleteByEspacoFisicoId(@Param("idEspaco") Integer idEspaco);

    @Modifying
    @Query("DELETE FROM Solicitacoes s WHERE s.usuarioAvaliador.idUsuario = :idUsuario OR s.usuarioSolicitante.idUsuario = :idUsuario")
    void deleteByUsuarioId(@Param("idUsuario") Integer idUsuario);
}

