package ucsal.cauzy.security.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@Order(2)
public class CustomSecurityConfig {

    private final CustomClientAuthenticationProvider customProvider;

    @Bean
    public SecurityFilterChain customSecurityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authManager = new ProviderManager(customProvider);

        http
                .csrf(csrf -> csrf.disable()) // ✅ sem warning
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/oauth2/token").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new CustomClientAuthenticationFilter(authManager), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

