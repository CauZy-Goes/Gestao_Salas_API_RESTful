package ucsal.cauzy.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ucsal.cauzy.domain.entity.Solicitacoes;

import java.util.List;

public interface SolicitacoesRepository extends JpaRepository<Solicitacoes, Integer> {

    // Buscar todas as solicitações feitas por um professor específico
     List<Solicitacoes> findByUsuarioSolicitante_IdUsuario(Integer idUsuarioSolicitante);
}

