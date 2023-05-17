package ch.hearc.SaphirLion.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.server.ResponseStatusException;

import ch.hearc.SaphirLion.model.User;
import ch.hearc.SaphirLion.model.UserMedia;
import ch.hearc.SaphirLion.repository.UserMediaRepository;

// https://www.baeldung.com/spring-mvc-custom-validator
public class BelongUserValidator implements Validator {

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
            errors.rejectValue("user", "userMedia.user.null",
                    "Aucun utilisateur connecté, impossible de savoir cet objet vous appartient");
            return;
        }

        // We could also add check if media exists, but :
        // Instead of "check if media exists & belong, we prompt "media not belong" even
        // if it doesn't exist, because we don't want to give information about the
        // existence of a media

        boolean result = userMediaRepository.belongsToUser(user.getId(), userMedia.getId());
        if (!result) {
            errors.rejectValue("user", "userMedia.user.notBelong", "Ce média ne vous appartient pas");
        }
    }
}