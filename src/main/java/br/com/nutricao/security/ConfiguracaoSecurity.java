package br.com.nutricao.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.nutricao.repositories.UsuarioRepository;

@Configuration
@EnableWebSecurity
public class ConfiguracaoSecurity {

    private final UsuarioRepository usuarioRepository;

    public ConfiguracaoSecurity(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Bean
    public JWTUtil jwtUtil(
            @Value("${jwt.secret:nutricao-api-secret-key-dev-2024}") String secret,
            @Value("${jwt.expiration-ms:86400000}") long expirationMs) {
        return new JWTUtil(secret, expirationMs);
    }

    @Bean
    public UsuarioDetailsService userDetailsService() {
        return new UsuarioDetailsService(usuarioRepository);
    }

    @Bean
    public FiltroSecurity jwtAuthenticationFilter(JWTUtil jwtUtil,
                                                           UsuarioDetailsService userDetailsService) {
        return new FiltroSecurity(jwtUtil, userDetailsService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           FiltroSecurity jwtAuthenticationFilter) throws Exception {
        http.csrf().disable()
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .antMatchers("/api/v1/auth/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/usuarios").permitAll()
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
            )
            .authenticationProvider(authenticationProvider(passwordEncoder(), userDetailsService()))
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder,
                                                         UsuarioDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
