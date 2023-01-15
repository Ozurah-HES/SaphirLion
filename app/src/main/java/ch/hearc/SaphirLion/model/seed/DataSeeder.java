package ch.hearc.SaphirLion.model.seed;

import java.util.Arrays;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ch.hearc.SaphirLion.repository.CategoryRepository;
import ch.hearc.SaphirLion.repository.MediaRepository;
import ch.hearc.SaphirLion.repository.TypeRepository;
import ch.hearc.SaphirLion.repository.UserMediaRepository;
import ch.hearc.SaphirLion.repository.UserRepository;

import ch.hearc.SaphirLion.model.Category;
import ch.hearc.SaphirLion.model.Media;
import ch.hearc.SaphirLion.model.Type;
import ch.hearc.SaphirLion.model.UserMedia;
import ch.hearc.SaphirLion.model.User;

@Component
public class DataSeeder implements CommandLineRunner {

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

    @Override
    public void run(String... args) throws Exception {

        Type type1 = new Type();
        type1.setType("Type 1");
        Type type2 = new Type();
        type2.setType("Type 2");
        typeRepository.saveAll(Arrays.asList(type1, type2));

        Category category1 = new Category();
        category1.setCategory("Category 1");
        Category category2 = new Category();
        category2.setCategory("Category 2");
        categoryRepository.saveAll(Arrays.asList(category1, category2));

        Media media1 = new Media();
        media1.setName("Media 1");
        media1.setType(type1);
        media1.setCategory(category1);
        Media media2 = new Media();
        media2.setName("Media 2");
        media2.setType(type2);
        media2.setCategory(category2);
        mediaRepository.saveAll(Arrays.asList(media1, media2));

        User user1 = new User();
        user1.setUsername("User 1");
        user1.setPasswordAndCrypt("password");
        User user2 = new User();
        user2.setUsername("User 2");
        user2.setPasswordAndCrypt("password");
        userRepository.saveAll(Arrays.asList(user1, user2));

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

        // READ DATA TEST :


        /* /!\ PROBLEME RENCONTRE : /!\ 
         // Pour avoir les medias chargés avec findAll(), il faudrais ajouter dans la classe Type : 
            Je sais pas ce qui est le mieux
            @OneToMany(mappedBy = "type", fetch = FetchType.EAGER)
            private Set<Media> medias = new TreeSet<Media>();
         */
        System.out.println("Test type->media findAllWithMedias() :");
        var types = typeRepository.findAllWithMedias();
        for (Type type : types) {
            for (Media media : type.getMedias()) {
                System.out.println(media.getName());
            }
        }

        System.out.println("Test type->media findMediasByType() :");
        for (Type type : types) {
            for (Media media : typeRepository.findMediasByType(type.getId())) {
                System.out.println(media.getName());
            }
        }

        System.out.println("Test media->type findAll() :");
        var medias = mediaRepository.findAll();
        for (Media media : medias) {
            System.out.println(media.getType().getType());
        }
    }
}