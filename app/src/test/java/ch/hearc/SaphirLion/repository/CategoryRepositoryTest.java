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
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void findAll_WithFetch() {
        // if we use "findAll()" instead of "findAllWithMedias()", we can't access to
        // the inner elements
        System.out.println("Test category->media findAllWithMedias() :");
        var categories = categoryRepository.findAllWithMedias();
        for (var category : categories) {
            assertDoesNotThrow(() -> {
                category.getMedias().forEach(System.out::println);
            });
        }

        // IE, this one will throw an exception
        // (normaly a "org.hibernate.LazyInitializationException")
        var categories2 = categoryRepository.findAll();
        for (var category : categories2) {
            assertThrows(Exception.class, () -> {
                category.getMedias().forEach(System.out::println);
            });
        }
    }

    @Test
    public void findAllLinkedMedia() {
        System.out.println("Test category->media findMediasByType() :");
        var categories = categoryRepository.findAllWithMedias();
        for (var category : categories) {
            categoryRepository.findAllMedias(category.getId()).forEach(
                    elem -> assertTrue(elem.getCategory().getId() == category.getId()));
        }
    }
}