package ch.hearc.SaphirLion.validator;

import org.springframework.beans.factory.annotation.Autowired;

import ch.hearc.SaphirLion.annotations.BelongConstraint;
import ch.hearc.SaphirLion.model.User;
import ch.hearc.SaphirLion.model.UserMedia;
import ch.hearc.SaphirLion.repository.UserMediaRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

// https://www.baeldung.com/spring-mvc-custom-validator
public class BelongValidator2 implements ConstraintValidator<BelongConstraint, UserMedia> {

    @Autowired
    private UserMediaRepository userMediaRepository;
    
    @Override
    public void initialize(BelongConstraint constraint) {
    }

    @Override
    public boolean isValid(UserMedia userMedia, ConstraintValidatorContext cxt) {
        if (userMedia.getId() == null) {
            return true;
        }

        User user = userMedia.getUser();
        if (user == null) {
            return false;
        }

        boolean result = userMediaRepository.belongsToUser(user.getId(), userMedia.getId());
        return result;
        // return true;
    }
}