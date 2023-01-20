package ch.hearc.SaphirLion.utils;

import org.springframework.ui.Model;

import ch.hearc.SaphirLion.model.User;

public class ControllerUtils {
    public static void modelCommonAttribute(Model model, User user, String currentPageName, String pageTitle) {
        model.addAttribute("currentPageName", currentPageName);
        model.addAttribute("title", pageTitle);
        model.addAttribute("user", user);
    }
}