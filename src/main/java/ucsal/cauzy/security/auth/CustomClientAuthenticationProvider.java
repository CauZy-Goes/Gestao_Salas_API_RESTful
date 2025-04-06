package ucsal.cauzy.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ucsal.cauzy.domain.service.ClientService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomClientAuthenticationProvider implements AuthenticationProvider {

    private final ClientService clientService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String clientId = authentication.getName();
        String clientSecret = authentication.getCredentials().toString();

        var client = clientService.obterPorClientID(clientId);

        if (client == null || !passwordEncoder.matches(clientSecret, client.getClientSecret())) {
            throw new BadCredentialsException("Invalid client credentials");
        }

        return new UsernamePasswordAuthenticationToken(clientId, clientSecret, List.of());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomClientAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
