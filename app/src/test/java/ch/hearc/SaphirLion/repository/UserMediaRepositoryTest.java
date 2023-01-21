package ch.hearc.SaphirLion.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import ch.hearc.SaphirLion.model.User;

@SpringBootTest
@TestPropertySource(locations = { "/application-test.properties", "/application-test-h2.properties" })
public class UserMediaRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMediaRepository userMediaRepository;

    @Test
    public void findByUserId() {
        User u = userRepository.findAll().iterator().next();

        var userMediasPage = userMediaRepository.findByUserId(u.getId(), null);
        var userMedias = userMediasPage.getContent();

        userMedias.forEach(usermedia -> {
            assertTrue(usermedia.getUser().getId() == u.getId());
        });
    }

    @Test
    public void belongsToUser() {
        User u = userRepository.findAll().iterator().next();
        var userMediasPage = userMediaRepository.findByUserId(u.getId(), null);
        var userMedias = userMediasPage.getContent();

        userMedias.forEach(usermedia -> {
            assertTrue(userMediaRepository.belongsToUser(usermedia.getUser().getId(), u.getId()));
        });

        userMedias.forEach(usermedia -> {
            assertFalse(userMediaRepository.belongsToUser(usermedia.getUser().getId(), 666L));
        });
    }
}