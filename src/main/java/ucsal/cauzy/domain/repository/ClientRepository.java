package ucsal.cauzy.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ucsal.cauzy.domain.entity.Cargo;
import ucsal.cauzy.domain.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    Client findByClientId(String clientId);
}
