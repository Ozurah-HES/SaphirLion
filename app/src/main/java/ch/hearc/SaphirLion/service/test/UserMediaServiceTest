package ch.hearc.SaphirLion.service.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ch.hearc.SaphirLion.model.UserMedia;
import ch.hearc.SaphirLion.repository.UserRepository;
import ch.hearc.SaphirLion.service.impl.MediaService;
import ch.hearc.SaphirLion.service.impl.UserMediaService;

@Component
public class UserMediaServiceTest implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMediaService userMediaService;

    @Autowired
    MediaService mediaService;

    @Override
    public void run(String... args) throws Exception {
        var user = userRepository.findByUsername("User 1");
        assert user != null;

        var medias = mediaService.readAll();
        assert medias != null && medias.size() > 0;

        // Create
        var newUserMedia = new UserMedia();
        newUserMedia.setUser(user);
        newUserMedia.setMedia(medias.get(0));
        newUserMedia.setLastSeen(0);
        newUserMedia.setNbOwned(1);
        newUserMedia.setNbPublished(2);
        newUserMedia.setRemark("Test remark");

        var um = userMediaService.save(newUserMedia);
        assert um.equals(newUserMedia);

        // Read
        assert userMediaService.read(newUserMedia.getId()).equals(um);
        var userMedias = userMediaService.readAllOfUser(user.getId(), null).getContent();
        assert userMedias != null;
        assert userMedias.size() > 0; // TODO : Can fail if db isn't seeded

        // Update
        newUserMedia.setLastSeen(1);
        newUserMedia.setNbOwned(2);
        newUserMedia.setNbPublished(3);
        newUserMedia.setRemark("Updated remark");
        um = userMediaService.save(newUserMedia);
        assert um.getLastSeen() == 1;
        assert um.getNbOwned() == 2;
        assert um.getNbPublished() == 3;
        assert um.getRemark().equals("Updated remark");

        // Delete
        userMediaService.delete(um.getId());
        userMedias = userMediaService.readAllOfUser(user.getId(), null).getContent();
        assert !userMedias.contains(newUserMedia);
        um = userMediaService.read(um.getId());
        assert um == null;
    }
}
