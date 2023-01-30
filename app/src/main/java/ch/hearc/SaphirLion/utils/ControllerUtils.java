package ch.hearc.SaphirLion.utils;

import org.springframework.ui.Model;

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
}