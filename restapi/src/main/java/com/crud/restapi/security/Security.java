package com.crud.restapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class Security
{

    @Bean
public UserDetailsManager userdetailsmanager(DataSource datasource)
{
   return new JdbcUserDetailsManager(datasource);

}

@Bean
    public SecurityFilterChain FilterChain(HttpSecurity http) throws Exception
{
    http.authorizeHttpRequests(configurer ->
            configurer.requestMatchers(HttpMethod.GET, "/api/companies").hasRole("EMPLOYEE")
                    .requestMatchers(HttpMethod.GET, "/api/companies/**").hasRole("EMPLOYEE")
                    .requestMatchers(HttpMethod.POST, "/api/companies").hasRole("MANAGER")
                    .requestMatchers(HttpMethod.PUT, "/api/companies/**").hasRole("MANAGER")
                    .requestMatchers(HttpMethod.PATCH, "/api/companies/**").hasRole("MANAGER")
                    .requestMatchers(HttpMethod.DELETE, "/api/companies/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/departments").hasRole("EMPLOYEE")
                    .requestMatchers(HttpMethod.GET, "/api/departments/**").hasRole("EMPLOYEE")
                    .requestMatchers(HttpMethod.POST, "/api/departments").hasRole("MANAGER")
                    .requestMatchers(HttpMethod.PUT, "/api/departments/**").hasRole("MANAGER")
                    .requestMatchers(HttpMethod.PATCH, "/api/departments/**").hasRole("MANAGER")
                    .requestMatchers(HttpMethod.DELETE, "/api/departments/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/tasks").hasRole("EMPLOYEE")
                    .requestMatchers(HttpMethod.GET, "/api/tasks/**").hasRole("EMPLOYEE")
                    .requestMatchers(HttpMethod.POST, "/api/tasks").hasRole("MANAGER")
                    .requestMatchers(HttpMethod.PUT, "/api/tasks/**").hasRole("MANAGER")
                    .requestMatchers(HttpMethod.PATCH, "/api/tasks/**").hasRole("MANAGER")
                    .requestMatchers(HttpMethod.DELETE, "/api/tasks/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/information").hasRole("EMPLOYEE")
                    .requestMatchers(HttpMethod.GET, "/api/information/**").hasRole("EMPLOYEE")
                    .requestMatchers(HttpMethod.POST, "/api/information").hasRole("MANAGER")
                    .requestMatchers(HttpMethod.PUT, "/api/information/**").hasRole("MANAGER")
                    .requestMatchers(HttpMethod.PATCH, "/api/information/**").hasRole("MANAGER")
                    .requestMatchers(HttpMethod.DELETE, "/api/information/**").hasRole("ADMIN")

    );
    http.httpBasic(Customizer.withDefaults());
    http.authorizeHttpRequests(
            authorizeRequests -> authorizeRequests.requestMatchers("/swagger-ui/**")
                    .permitAll()
                    .requestMatchers("/v3/api-docs*/**")
                    .permitAll());

    http.csrf(csrf -> csrf.disable());
    return http.build();
}

}
