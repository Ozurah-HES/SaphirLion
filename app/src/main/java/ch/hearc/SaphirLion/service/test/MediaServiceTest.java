package ch.hearc.SaphirLion.service.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ch.hearc.SaphirLion.model.Media;
import ch.hearc.SaphirLion.service.impl.AuthService;
import ch.hearc.SaphirLion.service.impl.MediaService;

@Component
public class MediaServiceTest implements CommandLineRunner {

    @Autowired
    AuthService auth;

    @Autowired
    MediaService mediaService;
    
    @Override
    public void run(String... args) throws Exception {
        var user = auth.authenticate("User 1", "password", null);
        assert user != null;

        // Read type & category
        var types = mediaService.readAllTypes();
        var categories = mediaService.readAllCategories();
        assert types.size() > 0;
        assert categories.size() > 0;

        // Create
        var newMedia = new Media();
        newMedia.setName("Test media");
        newMedia.setType(types.iterator().next());
        newMedia.setCategory(categories.iterator().next());
        var media = mediaService.create(newMedia);
        assert media.equals(newMedia);

        // Read
        var medias = mediaService.readAll();
        assert medias.contains(newMedia);

        var mediaOfUser = mediaService.readAllOfUser(user.getId());
        assert mediaOfUser != null;
        assert mediaOfUser.size() > 0; // TODO : Can fail if db isn't seeded

        // Update
        newMedia.setName("updated name");
        media = mediaService.update(newMedia);
        assert media.getName().equals("updated name");

        // Delete
        mediaService.delete(media.getId());
        medias = mediaService.readAll();
        assert !medias.contains(newMedia);
    } 
}
