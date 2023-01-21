package ch.hearc.SaphirLion.repository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = { "/application-test.properties", "/application-test-h2.properties" })
public class TypeRepositoryTest {
    @Autowired
    private TypeRepository typeRepository;

    @Test
    public void findAll_WithFetch() {
        // if we use "findAll()" instead of "findAllWithMedias()", we can't access to
        // the inner elements
        var types = typeRepository.findAllWithMedias();
        for (var type : types) {
            assertDoesNotThrow(() -> {
                type.getMedias().forEach(System.out::println);
            });
        }

        // IE, this one will throw an exception
        // (normaly a "org.hibernate.LazyInitializationException")
        var types2 = typeRepository.findAll();
        for (var type : types2) {
            assertThrows(Exception.class, () -> {
                type.getMedias().forEach(System.out::println);
            });
        }
    }

    @Test
    public void findAllLinkedMedia() {
        var types = typeRepository.findAllWithMedias();
        for (var type : types) {
            assertDoesNotThrow(() -> {
                typeRepository.findAllMedias(type.getId()).forEach(System.out::println);
            });
        }
    }
}