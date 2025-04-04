package ucsal.cauzy.domain.repository.Specs;

import org.springframework.data.jpa.domain.Specification;
import ucsal.cauzy.domain.entity.EspacoFisico;
import ucsal.cauzy.domain.entity.Solicitacoes;
import ucsal.cauzy.domain.entity.Status;
import ucsal.cauzy.domain.entity.Usuario;

public class SolicitacoesSpecs {

    public static Specification<Solicitacoes> usuarioAvaliadorEqual(Usuario usuario){
        return (root, query, cb) -> cb.equal(root.get("usuarioAvaliador"), usuario);
    }

    public static Specification<Solicitacoes> usuarioSolicitanteEqual(Usuario usuario){
        return (root, query, cb) -> cb.equal(root.get("usuarioSolicitante"), usuario);
    }

    public static Specification<Solicitacoes> usuarioStatusEqual(Status status){
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }

    public static Specification<Solicitacoes> usuarioEspacoFisicoEqual(EspacoFisico espacoFisico){
        return (root, query, cb) -> cb.equal(root.get("espacoFisico"), espacoFisico);
    }

}
