package ch.hearc.SaphirLion.controller.REST;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.hearc.SaphirLion.model.Media;
import ch.hearc.SaphirLion.model.User;
import ch.hearc.SaphirLion.service.impl.MediaService;
import ch.hearc.SaphirLion.service.impl.UserService;

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
}
