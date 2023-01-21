package ch.hearc.SaphirLion.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import ch.hearc.SaphirLion.model.Media;
import ch.hearc.SaphirLion.repository.UserRepository;
import ch.hearc.SaphirLion.service.impl.MediaService;

@SpringBootTest
@TestPropertySource(locations = { "/application-test.properties", "/application-test-h2.properties" })
public class MediaServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MediaService mediaService;

    @Test
    public void dbCRUD() {
        // user for testing
        var user = userRepository.findAll().iterator().next();
        assertNotNull(user);

        // Read type & category
        var types = mediaService.readAllTypes();
        var categories = mediaService.readAllCategories();
        assertTrue(types.size() > 0);
        assertTrue(categories.size() > 0);

        // Create
        var newMedia = new Media();
        newMedia.setName("Test media");
        newMedia.setType(types.get(0));
        newMedia.setCategory(categories.get(0));
        var media = mediaService.save(newMedia);
        assertTrue(media.equals(newMedia));
        assertEquals(mediaService.read(newMedia.getId()), media);

        // Read
        var medias = mediaService.readAll();
        assertTrue(medias.contains(newMedia));

        var mediaOfUser = mediaService.readAllOfUser(user.getId());
        assertNotNull(mediaOfUser);
        assertTrue(mediaOfUser.size() > 0);

        // Update
        newMedia.setName("updated name");
        media = mediaService.save(newMedia);
        assertEquals(media.getName(), "updated name");

        // Delete
        mediaService.delete(media.getId());
        medias = mediaService.readAll();
        assertFalse(medias.contains(newMedia));
        media = mediaService.read(media.getId());
        assertNull(media);
    }
}
