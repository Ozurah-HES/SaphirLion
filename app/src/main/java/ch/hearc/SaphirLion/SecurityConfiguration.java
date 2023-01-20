// tuto : https://openclassrooms.com/fr/courses/7137776-securisez-votre-application-web-avec-spring-security/7275511-configurez-une-page-de-connexion-customisee-avec-spring-security
package ch.hearc.SaphirLion;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import ch.hearc.SaphirLion.service.impl.UserDetailServiceImpl;

@Configuration
@EnableWebSecurity
@Profile(value = "secure")
public class SecurityConfiguration {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        localeResolver.setDefaultLocale(Locale.FRENCH);
        return localeResolver;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // https://stackoverflow.com/questions/62531927/spring-security-redirect-to-static-resources-after-authentication
        // csrf : https://www.baeldung.com/spring-security-csrf
        
        http.csrf().disable().authorizeHttpRequests()
                .requestMatchers("/js/**", "/css/**", "/images/**").permitAll()
                .requestMatchers("/", "/home", "/index").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll();

        http.logout()
                // remove the logout confirmation
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/");

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailServiceImpl();
    }
}
