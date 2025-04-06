package ucsal.cauzy.security.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class CustomClientAuthenticationToken extends AbstractAuthenticationToken {

    private final String clientId;
    private final String clientSecret;

    public CustomClientAuthenticationToken(String clientId, String clientSecret) {
        super(null);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return this.clientSecret;
    }

    @Override
    public Object getPrincipal() {
        return this.clientId;
    }
}

