package ch.hearc.SaphirLion.service.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import ch.hearc.SaphirLion.repository.UserRepository;
import ch.hearc.SaphirLion.service.impl.AuthService;

@Component
public class AuthServiceTest implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public void run(String... args) throws Exception {

        var auth = new AuthService(userRepository, new BCryptPasswordEncoder());

        var user = auth.authenticate("User 1", "password", null);
        var userBadPassword = auth.authenticate("User 1", "psw", null);
        var userBadName = auth.authenticate("unknown", "password", null);

        // Needs to be run with -ea flag
        assert user != null;
        assert user.getUsername().equals("User 1");
        assert userBadPassword == null;
        assert userBadName == null;
    } 
}
