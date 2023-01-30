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

import ch.hearc.SaphirLion.model.UserMedia;
import ch.hearc.SaphirLion.repository.UserRepository;
import ch.hearc.SaphirLion.service.impl.MediaService;
import ch.hearc.SaphirLion.service.impl.UserMediaService;

@SpringBootTest
@TestPropertySource(locations = { "/application-test.properties", "/application-test-h2.properties" })
public class UserMediaServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMediaService userMediaService;

    @Autowired
    MediaService mediaService;

    @Test
    public void dbCRUD() {
        // user for testing
        var user = userRepository.findByUsername("User 1");
        assertNotNull(user);

        // medias for testing (we need to asign a media to the UserMedia to create)
        var medias = mediaService.readAll();
        assertNotNull(medias);
        assertTrue(medias.size() > 0);

        // Create
        var newUserMedia = new UserMedia();
        newUserMedia.setUser(user);
        newUserMedia.setMedia(medias.get(0));
        newUserMedia.setLastSeen(0);
        newUserMedia.setNbOwned(1);
        newUserMedia.setNbPublished(2);
        newUserMedia.setRemark("Test remark");

        var um = userMediaService.save(newUserMedia);
        assertEquals(um, newUserMedia);

        // Read
        assertEquals(userMediaService.read(newUserMedia.getId()), um);

        var userMedias = userMediaService.readAllOfUser(user.getId(), null).getContent();
        assertNotNull(userMedias);
        assertTrue(userMedias.size() > 0);

        // Update
        newUserMedia.setLastSeen(1);
        newUserMedia.setNbOwned(2);
        newUserMedia.setNbPublished(3);
        newUserMedia.setRemark("Updated remark");

        um = userMediaService.save(newUserMedia);

        assertTrue(um.getLastSeen() == 1);
        assertTrue(um.getNbOwned() == 2);
        assertTrue(um.getNbPublished() == 3);
        assertEquals(um.getRemark(), "Updated remark");

        // Delete
        userMediaService.delete(um.getId());
        userMedias = userMediaService.readAllOfUser(user.getId(), null).getContent();
        assertFalse(userMedias.contains(newUserMedia));

        um = userMediaService.read(um.getId());
        assertNull(um);
    }
}
