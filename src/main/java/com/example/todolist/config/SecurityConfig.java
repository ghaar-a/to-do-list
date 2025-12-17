package com.example.todolist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuração de segurança da aplicação usando Spring Security.
 * <p>
 * Para este projeto de portfólio, utilizamos:
 * - Autenticação básica (HTTP Basic) com usuário em memória
 * - Todas as rotas da API protegidas
 * - CSRF desabilitado (facilita testes com Postman/curl)
 * <p>
 * Em produção, recomenda-se trocar por JWT, OAuth2 ou usuários persistidos no banco.
 */
@Configuration
public class SecurityConfig {

    /**
     * Define as regras de segurança para as requisições HTTP.
     *
     * @param http o objeto HttpSecurity para configuração
     * @return a cadeia de filtros de segurança
     * @throws Exception em caso de erro na configuração
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated()  // Todas as rotas exigem autenticação
            )
            .httpBasic(Customizer.withDefaults())  // Autenticação básica
            .csrf(csrf -> csrf.disable());         // Desabilita CSRF (comum em APIs REST puras)

        return http.build();
    }

    /**
     * Configura um usuário em memória para testes e demonstração.
     * <p>
     * Usuário padrão:
     * - Username: user
     * - Password: password
     * <p>
     * Usamos {noop} para indicar que a senha está em texto plano (apenas para desenvolvimento).
     * Isso evita o uso do método deprecated e mantém a senha legível nos logs.
     *
     * @return serviço de detalhes do usuário em memória
     */
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password("{noop}password")  // {noop} = No Operation Password Encoder (senha em texto plano)
                .roles("USER")
                .build();

        // Em produção: usar BCryptPasswordEncoder e armazenar usuários no banco

        return new InMemoryUserDetailsManager(user);
    }
}