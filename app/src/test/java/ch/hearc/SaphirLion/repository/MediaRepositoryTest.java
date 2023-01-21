package ch.hearc.SaphirLion.repository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = { "/application-test.properties", "/application-test-h2.properties" })
public class MediaRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MediaRepository mediaRepository;

    @Test
    public void findAll_WithFetch() {
        // if we use "findAll()" instead of "findAllWithMedias()", we can't access to
        // the inner elements
        var medias = mediaRepository.findAllWithUserMedias();
        for (var media : medias) {
            assertDoesNotThrow(() -> {
                media.getUserMedias().forEach(System.out::println);
            });
        }

        // IE, this one will throw an exception
        // (normaly a "org.hibernate.LazyInitializationException")
        var medias2 = mediaRepository.findAll();
        for (var media : medias2) {
            assertThrows(Exception.class, () -> {
                media.getUserMedias().forEach(System.out::println);
            });
        }
    }

    @Test
    public void findAllLinkedUserMedia() {
        var medias = mediaRepository.findAllWithUserMedias();
        for (var media : medias) {
            mediaRepository.findAllUserMedia(media.getId()).forEach(
                    elem -> assertTrue(elem.getMedia().getId() == media.getId()));
        }
    }

    @Test
    public void findAllLinkedUser() {

        var users = userRepository.findAllWithUserMedias();
        for (var user : users) {
            mediaRepository.findAllOfUser(user.getId()).forEach(
                    elem -> assertTrue(user.getUserMedias().stream().anyMatch(
                            userMedia -> userMedia.getMedia().getId() == elem.getId())));
        }
    }
}