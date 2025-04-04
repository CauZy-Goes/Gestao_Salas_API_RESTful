package ucsal.cauzy.domain.repository.Specs;

import org.springframework.data.jpa.domain.Specification;
import ucsal.cauzy.domain.entity.EspacoFisico;
import ucsal.cauzy.domain.entity.Solicitacoes;
import ucsal.cauzy.domain.entity.Status;
import ucsal.cauzy.domain.entity.Usuario;

public class SolicitacoesSpecs {

    public static Specification<Solicitacoes> usuarioAvaliadorEqual(Integer usuario){
        return (root, query, cb) -> cb.equal(root.get("usuarioAvaliador").get("idUsuario"), usuario);
    }

    public static Specification<Solicitacoes> usuarioSolicitanteEqual(Integer usuario){
        return (root, query, cb) -> cb.equal(root.get("usuarioSolicitante").get("idUsuario"), usuario);
    }

    public static Specification<Solicitacoes> usuarioStatusEqual(Integer status){
        return (root, query, cb) -> cb.equal(root.get("status").get("idStatus"), status);
    }

    public static Specification<Solicitacoes> usuarioEspacoFisicoEqual(Integer espacoFisico){
        return (root, query, cb) -> cb.equal(root.get("espacoFisico").get("idEspacoFisico"), espacoFisico);
    }

}
