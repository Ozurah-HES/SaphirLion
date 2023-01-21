package ch.hearc.SaphirLion.model.seed;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import ch.hearc.SaphirLion.model.Category;
import ch.hearc.SaphirLion.model.Media;
import ch.hearc.SaphirLion.model.Type;
import ch.hearc.SaphirLion.model.User;
import ch.hearc.SaphirLion.model.UserMedia;
import ch.hearc.SaphirLion.repository.CategoryRepository;
import ch.hearc.SaphirLion.repository.MediaRepository;
import ch.hearc.SaphirLion.repository.TypeRepository;
import ch.hearc.SaphirLion.repository.UserMediaRepository;
import ch.hearc.SaphirLion.repository.UserRepository;

@Order(2)
@Profile("seed-test")
@Component
public class DataSeederTest implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserMediaRepository userMediaRepository;

    // Seed test data for testing with multiple values
    // 2 user ("User 1", "User 2" ; with password "password")
    @Override
    public void run(String... args) throws Exception {
        // Seed types
        Type type1 = new Type();
        type1.setType("Type Test 1");
        Type type2 = new Type();
        type2.setType("Type Test 2");
        typeRepository.saveAll(Arrays.asList(type1, type2));

        // Seed categories
        Category category1 = new Category();
        category1.setCategory("Category Test 1");
        Category category2 = new Category();
        category2.setCategory("Category Test 2");
        categoryRepository.saveAll(Arrays.asList(category1, category2));

        // Seed medias
        Media media1 = new Media();
        media1.setName("Media Test 1");
        media1.setType(type1);
        media1.setCategory(category1);
        Media media2 = new Media();
        media2.setName("Media Test 2");
        media2.setType(type2);
        media2.setCategory(category2);
        // Test change order to see if it works when testing
        mediaRepository.saveAll(Arrays.asList(media2, media1));

        // Seed users
        User user1 = new User();
        user1.setUsername("User 1");
        user1.setPasswordAndCrypt("password");
        User user2 = new User();
        user2.setUsername("User 2");
        user2.setPasswordAndCrypt("password");
        userRepository.saveAll(Arrays.asList(user1, user2));

        // Seed userMedia
        UserMedia userMedia1 = new UserMedia();
        userMedia1.setUser(user1);
        userMedia1.setMedia(media1);
        userMedia1.setNbOwned(2);
        userMedia1.setNbPublished(3);
        userMedia1.setLastSeen(1);
        userMedia1.setRemark("Remark 1");
        UserMedia userMedia2 = new UserMedia();
        userMedia2.setUser(user2);
        userMedia2.setMedia(media2);
        userMedia2.setNbOwned(1);
        userMedia2.setNbPublished(2);
        userMedia2.setLastSeen(0);
        userMedia2.setRemark("Remark 2");
        userMediaRepository.saveAll(Arrays.asList(userMedia1, userMedia2));

        // Seed multiple userMedia for user "Test 1"
        for (int i = 0; i < 30; i++) {
            Media media = new Media();
            media.setName("TheSerie Arc " + i);
            media.setType(type1);
            media.setCategory(category1);
            mediaRepository.save(media);

            UserMedia userMedia = new UserMedia();
            userMedia.setUser(user1);
            userMedia.setMedia(media);
            userMedia.setNbOwned(100 + i);
            userMedia.setNbPublished(200 + i);
            userMedia.setLastSeen(i);
            userMedia.setRemark("Big collection " + i);
            userMediaRepository.save(userMedia);
        }

        // READ DATA TEST :

        System.out.println("Test type->media findAllWithMedias() :");
        var types = typeRepository.findAllWithMedias();
        for (Type type : types) {
            for (Media media : type.getMedias()) {
                System.out.println(media.getName());
            }
        }

        System.out.println("Test type->media findMediasByType() :");
        for (Type type : types) {
            for (Media media : typeRepository.findAllMedias(type.getId())) {
                System.out.println(media.getName());
            }
        }

        System.out.println("Test media->type findAll() :");
        var medias = mediaRepository.findAll();
        for (Media media : medias) {
            System.out.println(media.getType().getType());
        }

        System.out.println("Test usermedia->media :");
        var userMedias = userMediaRepository.findAll();
        for (UserMedia um : userMedias) {
            System.out.println(um.getMedia().getName() + " " + um.getMedia().getCategory().getCategory());
        }

        System.out.println("Test user->media :");
        var users = userRepository.findAllWithUserMedias();
        for (User user : users) {
            for (UserMedia userMedia : user.getUserMedias()) {
                System.out.println(userMedia.getMedia().getName() + " " + userMedia.getMedia().getType().getType());
            }
        }
    }
}