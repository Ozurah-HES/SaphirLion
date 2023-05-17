package ch.hearc.SaphirLion.controller.REST;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.hearc.SaphirLion.model.User;
import ch.hearc.SaphirLion.model.UserMedia;
import ch.hearc.SaphirLion.service.impl.MediaService;
import ch.hearc.SaphirLion.service.impl.UserMediaService;
import ch.hearc.SaphirLion.validator.BelongUserValidator;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserMediaRestController {

    @Autowired
    private UserMediaService userMediaService;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private Validator validator;

    @Autowired
    private BelongUserValidator belongValidator;

    @GetMapping(value = "/user/medias", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> index(@RequestBody User user) {
        Page<UserMedia> usermedias = userMediaService.readAllOfUser(user.getId(), null);

        String jsonResponse = null;
        try {
            jsonResponse = new ObjectMapper().writeValueAsString(usermedias.getContent());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON)
                    .body("[\"Error while serializing data to json\"]");
        }

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(jsonResponse);
    }

    @PutMapping(value = "/user/media", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> edit(@RequestBody JsonNode requestBody, BindingResult errors) {
        ObjectMapper objectMapper = new ObjectMapper();
        // Disable fail on unknown properties to be able to retrieve "non property" of
        // UserMedia and Also be able to auto-serialize usermedia
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        User u = new User();
        UserMedia userMedia = null;

        try {
            u.setId(requestBody.get("userId").asLong());
            userMedia = objectMapper.treeToValue(requestBody, UserMedia.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("[\"json parse failed\"]");
        }

        Long mediaId = requestBody.get("mediaId").asLong();
        if (!mediaService.idExists(mediaId)) {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON)
                    .body("[\"media id does not exist\"]");
        }

        userMedia.setUser(u);
        userMedia.setMedia(mediaService.read(mediaId));

        UserMedia umCreated = userMediaService.save(userMedia);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body("[\"Edited item Id\" :" + umCreated.getId() + "]");
    }

    @DeleteMapping(value = "/user/media", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> delete(@RequestBody JsonNode requestBody, BindingResult errors) {
        ObjectMapper objectMapper = new ObjectMapper();
        // Disable fail on unknown properties to be able to retrieve "non property" of
        // UserMedia and Also be able to auto-serialize usermedia
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        User u = new User();
        UserMedia userMedia = null;

        try {
            u.setId(requestBody.get("userId").asLong());
            userMedia = objectMapper.treeToValue(requestBody, UserMedia.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("[\"json parse failed\"]");
        }

        userMedia.setUser(u);

        BindingResult userMediaErrors = new BeanPropertyBindingResult(userMedia, "UserMedia");

        belongValidator.validate(userMedia, userMediaErrors);
        if (errors.hasErrors() || userMediaErrors.hasErrors()) {
            List<String> errorsResponse = new ArrayList<>();
            for (FieldError error : errors.getFieldErrors()) {
                System.out.println(error.getDefaultMessage());
                errorsResponse.add(error.getDefaultMessage());
            }
            for (FieldError error : userMediaErrors.getFieldErrors()) {
                System.out.println(error.getDefaultMessage());
                errorsResponse.add(error.getDefaultMessage());
            }

            String errorsJson = null;
            try {
                errorsJson = objectMapper.writeValueAsString(errorsResponse);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorsJson);
        }

        userMediaService.delete(userMedia.getId());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body("[\"Deleted item ID\" : " + userMedia.getId() + "]");
    }
}