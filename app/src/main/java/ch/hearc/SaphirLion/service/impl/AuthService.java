// Tuto bcrypt : 
// https://www.aegissofttech.com/articles/bcrypt-password-encoding-spring-security.html
// Offical doc :
// https://docs.spring.io/spring-security/site/docs/5.0.x/reference/html/crypto.html
// https://mvnrepository.com/artifact/org.springframework.security/spring-security-crypto
// Other interesting links (not followed) :
// https://spring.io/guides/gs/securing-web/
package ch.hearc.SaphirLion.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ch.hearc.SaphirLion.model.User;
import ch.hearc.SaphirLion.repository.UserRepository;
import ch.hearc.SaphirLion.service.AuthService_I;
import jakarta.servlet.http.HttpSession;

/**
 * Service for authentication of a user on the website
 * @deprecated Remplaced by Spring Security
 */
@Deprecated
@Service
public class AuthService implements AuthService_I {
    @Autowired
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User authenticate(String username, String password, HttpSession session) {
        User user = userRepository.findByUsername(username);

        if (user != null && bCryptPasswordEncoder.matches(password, user.getPassword())) {
            if (session != null)
                session.setAttribute("user", user);
            return user;
        }
        return null;
    }

    @Override
    public User connectedUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }
}
