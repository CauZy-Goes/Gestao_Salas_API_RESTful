package ucsal.cauzy.security.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CustomClientAuthenticationFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;

    public CustomClientAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        if ("/oauth2/token".equals(request.getRequestURI()) && "POST".equalsIgnoreCase(request.getMethod())) {
            String clientId = request.getParameter("client_id");
            String clientSecret = request.getParameter("client_secret");

            if (clientId != null && clientSecret != null) {
                CustomClientAuthenticationToken authRequest =
                        new CustomClientAuthenticationToken(clientId, clientSecret);

                try {
                    Authentication authResult = authenticationManager.authenticate(authRequest);
                    SecurityContextHolder.getContext().setAuthentication(authResult);
                } catch (AuthenticationException e) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed");
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}

