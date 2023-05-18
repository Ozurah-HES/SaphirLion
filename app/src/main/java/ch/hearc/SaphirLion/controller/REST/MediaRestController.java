package ch.hearc.SaphirLion.controller.REST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.hearc.SaphirLion.model.Media;
import ch.hearc.SaphirLion.service.impl.MediaService;
import ch.hearc.SaphirLion.utils.ControllerUtils;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class MediaRestController {

    @Autowired
    private MediaService mediaService;

    @GetMapping(value = "/medias", produces = "application/json")
    public ResponseEntity<String> index() {
        List<Media> users = mediaService.readAll();

        String jsonResponse = null;
        try {
            jsonResponse = new ObjectMapper().writeValueAsString(users);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("[\"Error while serializing data to json\"]");
        }

        return ResponseEntity.ok().body(jsonResponse);
    }

    @PutMapping(value = "/media", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> edit(@Valid @RequestBody Media media, BindingResult errors) {
        if (mediaService.nameExistsInOther(media.getId(), media.getName())) {
            errors.rejectValue("name", "name.exists", "Name already exists");
        }

        String errorsAsJson = ControllerUtils.OnValidationErrorToJson(errors.getFieldErrors());
        if (errorsAsJson != null && !errorsAsJson.isEmpty()) {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON)
                    .body(errorsAsJson);
        }

        // TODO (for next version) : add category and type choice possibility
        media.setCategory(mediaService.readAllCategories().get(0));
        media.setType(mediaService.readAllTypes().get(0));

        Media mCreated = mediaService.save(media);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body("[\"Edited item Id\" :" + mCreated.getId() + "]");
    }
}
