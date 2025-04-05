package ucsal.cauzy.security.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;
import ucsal.cauzy.domain.service.ClientService;

@Component
@RequiredArgsConstructor
public class CustomRegisteredClientRepository implements RegisteredClientRepository {

    private final ClientService clientService;

    @Override
    public void save(RegisteredClient registeredClient) {
        // não utilizado por enquanto
    }

    @Override
    public RegisteredClient findById(String id) {
        return null;
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        var client = clientService.obterPorClientID(clientId);
        if (client == null) return null;

        RegisteredClient.Builder builder = RegisteredClient
                .withId(client.getIdClient().toString())
                .clientId(client.getClientId())
                .clientSecret(client.getClientSecret()) // ✅ sem {bcrypt}
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .tokenSettings(TokenSettings.builder().build())
                .clientSettings(ClientSettings.builder().build());

        if (client.getScope() != null && !client.getScope().isBlank()) {
            for (String scope : client.getScope().split("\\s+")) {
                builder.scope(scope);
            }
        }

        return builder.build();
    }
}

