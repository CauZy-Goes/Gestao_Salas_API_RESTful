package ucsal.cauzy.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Client {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer idClient;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "client_secret")
    private String clientSecret;

    @Column(name = "redirect_uri")
    private String redirectURI;

    @Column(name = "scope")
    private String scope;
}
