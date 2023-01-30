package ch.hearc.SaphirLion.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ch.hearc.SaphirLion.model.User;
import ch.hearc.SaphirLion.utils.ControllerUtils;

@Controller
public class IndexController {

    @GetMapping({ "/", "/home", "/index" })
    public String home(Model model, @AuthenticationPrincipal User user) {
        // User user = (User) session.getAttribute("user"); // If using the old AuthService instead of Spring Security

        ControllerUtils.modelCommonAttribute(model, user, "home", "Home");
        return "index";
    }
}