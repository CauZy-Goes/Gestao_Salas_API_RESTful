package ucsal.cauzy.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ucsal.cauzy.domain.entity.LogAcoes;

public interface LogAcoesRepository extends JpaRepository<LogAcoes, Integer> {

    @Modifying
    @Query("DELETE FROM LogAcoes l WHERE l.usuario.idUsuario = :idUsuario")
    void deleteByUsuarioId(@Param("idUsuario") Integer idUsuario);
}

