package ch.hearc.SaphirLion.controller.REST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.hearc.SaphirLion.model.User;
import ch.hearc.SaphirLion.service.impl.UserService;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users", produces = "application/json")
    public ResponseEntity<String> index() {
        List<User> users = userService.readAll();

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
