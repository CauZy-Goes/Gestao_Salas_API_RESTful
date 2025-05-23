package ucsal.cauzy.security.configuration;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import ucsal.cauzy.domain.repository.UsuarioRepository;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import ucsal.cauzy.security.auth.JwtCustomAuthenticationFilter;
import ucsal.cauzy.security.service.UsuarioDetailsService;

import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtCustomAuthenticationFilter jwtCustomAuthenticationFilter;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Não autorizado")
                        )
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login/**", "/css/**", "/js/**", "/actuator/**", "/webhook/**", "/webhook").permitAll()
                        .requestMatchers("/oauth2/token").permitAll()
                        .anyRequest().authenticated()
                )

                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                )
                .addFilterAfter(jwtCustomAuthenticationFilter, BearerTokenAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UsuarioDetailsService(usuarioRepository);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html",
                "/swagger-resources/**", "/webjars/**", "/actuator/**", "/webhook", "/webhook/**"
        );
    }

    // ✅ Converte o scope do JWT para ROLE_
    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthorityPrefix("ROLE_");
        converter.setAuthoritiesClaimName("scope");

        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();

        // Envolve o conversor original para transformar em maiúsculo
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwt ->
                converter.convert(jwt)
                        .stream()
                        .map(grantedAuthority ->
                                new SimpleGrantedAuthority(grantedAuthority.getAuthority().toUpperCase())
                        )
                        .collect(Collectors.toList())
        );

        return jwtConverter;
    }

}


