package ch.hearc.SaphirLion.service.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ch.hearc.SaphirLion.model.Media;
import ch.hearc.SaphirLion.repository.UserRepository;
import ch.hearc.SaphirLion.service.impl.MediaService;

@Component
public class MediaServiceTest implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MediaService mediaService;
    
    @Override
    public void run(String... args) throws Exception {
        var user = userRepository.findByUsername("User 1");
        assert user != null;

        // Read type & category
        var types = mediaService.readAllTypes();
        var categories = mediaService.readAllCategories();
        assert types.size() > 0;
        assert categories.size() > 0;

        // Create
        var newMedia = new Media();
        newMedia.setName("Test media");
        newMedia.setType(types.get(0));
        newMedia.setCategory(categories.get(0));
        var media = mediaService.create(newMedia);
        assert media.equals(newMedia);
        assert mediaService.read(newMedia.getId()).equals(media);

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
        media = mediaService.read(media.getId());
        assert media == null;
    } 
}
