package hr.dice.luka_jurkic.config.security;

import hr.dice.luka_jurkic.config.CustomLogoutSuccessHandler;
import hr.dice.luka_jurkic.persistence.entity.UserEntity;
import hr.dice.luka_jurkic.persistence.repository.UserRepository;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class BasicAuthWebSecurityConfiguration {

    private final UserRepository userRepository;

    private final String[] API_GET_URL_WHITELIST = {
            "/api/users/{username}",
            "/api/ingredients/**",
            "/api/recipes/**",
            "/api/recipes/{recipeName}/ingredients",
            "/api/comments/recipe/{recipeName}",
            "/api/comments/details/{recipeName}"
    };

    private final String[] API_POST_URL_WHITELIST = {
            "/api/users"
    };
    private final String[] MVC_URLS_WHITELIST = {
            "/users/new",
            "/users/create",
            "/users",
            "/index",
            "/",
            "/login",
            "/recipes",
            "recipes/exportToPdf/{recipeName}"
    };

    public BasicAuthWebSecurityConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/error").permitAll()
                        .requestMatchers(HttpMethod.GET, API_GET_URL_WHITELIST).permitAll()
                        .requestMatchers(HttpMethod.POST, API_POST_URL_WHITELIST).permitAll()
                        .requestMatchers(MVC_URLS_WHITELIST).permitAll()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .formLogin(form ->
                        form.loginPage("/login")
                                .loginProcessingUrl("/authenticate")
                                .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .logoutSuccessHandler(new CustomLogoutSuccessHandler()));
        return httpSecurity.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {

        List<UserEntity> users = userRepository.findAll();
        List<UserDetails> usersDetails = new ArrayList<>();

        for (UserEntity user : users) {
            UserDetails userDetails = User.withUsername(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole().toString())
                    .build();
            usersDetails.add(userDetails);
        }
        return new InMemoryUserDetailsManager(usersDetails);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2A, 8);
    }
}
