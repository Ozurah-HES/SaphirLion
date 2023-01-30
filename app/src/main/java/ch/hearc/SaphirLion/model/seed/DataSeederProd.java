package ch.hearc.SaphirLion.model.seed;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import ch.hearc.SaphirLion.model.Category;
import ch.hearc.SaphirLion.model.Type;
import ch.hearc.SaphirLion.model.User;
import ch.hearc.SaphirLion.repository.CategoryRepository;
import ch.hearc.SaphirLion.repository.TypeRepository;
import ch.hearc.SaphirLion.repository.UserRepository;

/**
 * <p>Seed data only if there is no data for specific table</p>
 * <p>V1 : 2 user, 1 type, 1 category</p>
 * <p>Available users : "User 1", "User 2" ; with password "password"</p>
 */
@Order(2)
@Profile("seed-prod")
@Component
public class DataSeederProd implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        if (typeRepository.count() == 0) {
            Type type1 = new Type();
            type1.setType("Type 1");
            typeRepository.save(type1);
        }

        if (categoryRepository.count() == 0) {
            Category category1 = new Category();
            category1.setCategory("Category 1");
            categoryRepository.save(category1);
        }

        if (userRepository.count() == 0) {
            User user1 = new User();
            user1.setUsername("User 1");
            user1.setPasswordAndCrypt("password");
            User user2 = new User();
            user2.setUsername("User 2");
            user2.setPasswordAndCrypt("password");
            userRepository.saveAll(Arrays.asList(user1, user2));
        }
    }
}