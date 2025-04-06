package ucsal.cauzy.security.configuration;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import ucsal.cauzy.domain.entity.Usuario;

@Getter
public class CustomAuthentication extends AbstractAuthenticationToken {

    private final Usuario usuario;

    public CustomAuthentication(Usuario usuario) {
        super(usuario.getAuthorities()); // se quiser, pode converter cargos para authorities aqui
        this.usuario = usuario;
        super.setAuthenticated(true); // indica que já está autenticado
    }

    @Override
    public Object getCredentials() {
        return null; // sem necessidade, já autenticado
    }

    @Override
    public Object getPrincipal() {
        return usuario;
    }
}
