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
public class BelongConnectedValidator implements Validator {

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

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user == null) {
            errors.rejectValue("user", "userMedia.user.null",
                    "Aucun utilisateur connecté, impossible de savoir cet objet vous appartient");
            return;
        }

        boolean result = userMediaRepository.belongsToUser(user.getId(), userMedia.getId());
        if (!result) {
            //errors.rejectValue("user", "userMedia.user.notBelong", "Ce média ne vous appartient pas");

            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}