package ch.hearc.SaphirLion.repository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = { "/application-test.properties", "/application-test-h2.properties" })
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void findAll_WithFetch() {
        // if we use "findAll()" instead of "findAllWithMedias()", we can't access to
        // the inner elements
        var users = userRepository.findAllWithUserMedias();
        for (var user : users) {
            assertDoesNotThrow(() -> {
                user.getUserMedias().forEach(System.out::println);
            });
        }

        // IE, this one will throw an exception
        // (normaly a "org.hibernate.LazyInitializationException")
        var users2 = userRepository.findAll();
        for (var user : users2) {
            assertThrows(Exception.class, () -> {
                user.getUserMedias().forEach(System.out::println);
            });
        }
    }

    @Test
    public void findAllLinkedUserMedia() {
        var users = userRepository.findAllWithUserMedias();
        for (var user : users) {
            userRepository.findAllUserMedia(user.getId()).forEach(
                    elem -> assertTrue(elem.getUser().getId() == user.getId()));
        }
    }

    @Test
    public void findByUsername() {
        var users = userRepository.findAll();
        users.forEach(user -> {
            assertNotNull(userRepository.findByUsername(user.getUsername()));
        });
    }
}