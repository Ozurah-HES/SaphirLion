package ch.hearc.SaphirLion.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.hearc.SaphirLion.model.User;

public class ControllerUtils {
    /**
     * Add common attributes to the model
     * @param model The model where add the attributes
     * @param user the connected user
     * @param currentPageName the name of the current page (used for debug)
     * @param pageTitle the title of the page (used for the page tab and the page title)
     */
    public static void modelCommonAttribute(Model model, User user, String currentPageName, String pageTitle) {
        model.addAttribute("currentPageName", currentPageName);
        model.addAttribute("title", pageTitle);
        model.addAttribute("user", user);
    }

    
    public static String OnValidationErrorToJson(List<FieldError> errors) {
        if (errors == null || errors.isEmpty()) {
            return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        List<String> errorsResponse = new ArrayList<>();

        for (FieldError error : errors) {
            System.out.println(error.getDefaultMessage());
            errorsResponse.add(error.getDefaultMessage());
        }

        String errorsJson = null;
        try {
            errorsJson = objectMapper.writeValueAsString(errorsResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return errorsJson;
    }
}