package ch.hearc.SaphirLion.model.seed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import ch.hearc.SaphirLion.repository.CategoryRepository;
import ch.hearc.SaphirLion.repository.MediaRepository;
import ch.hearc.SaphirLion.repository.TypeRepository;
import ch.hearc.SaphirLion.repository.UserMediaRepository;
import ch.hearc.SaphirLion.repository.UserRepository;

/**
 * Clean the database tables
 */
@Order(1)
@Profile({"clean-db", "seed-test"})
@Component
public class DBCleaner implements CommandLineRunner {

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
        userMediaRepository.deleteAll();
        mediaRepository.deleteAll();
        userRepository.deleteAll();
        typeRepository.deleteAll();
        categoryRepository.deleteAll();
    }
}