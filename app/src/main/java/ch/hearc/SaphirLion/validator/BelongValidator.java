package ch.hearc.SaphirLion.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ch.hearc.SaphirLion.model.User;
import ch.hearc.SaphirLion.model.UserMedia;
import ch.hearc.SaphirLion.repository.UserMediaRepository;

// https://www.baeldung.com/spring-mvc-custom-validator
public class BelongValidator implements Validator {

    @Autowired
    private UserMediaRepository userMediaRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserMedia.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserMedia userMedia = (UserMedia) target;
        if (userMedia.getId() == null) {
            return;
        }

        User user = userMedia.getUser();
        if (user == null) {
            errors.rejectValue("user", "userMedia.user.null", "L'utilisateur du média spécifié n'a pas été défini");
            return;
        }

        boolean result = userMediaRepository.belongsToUser(user.getId(), userMedia.getId());
        if (!result) {
            errors.rejectValue("user", "userMedia.user.notBelong", "Ce média ne vous appartient pas");
        }
        
    }
}