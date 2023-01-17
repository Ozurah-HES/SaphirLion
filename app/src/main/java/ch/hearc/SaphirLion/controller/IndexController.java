package ch.hearc.SaphirLion.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ch.hearc.SaphirLion.model.User;
import ch.hearc.SaphirLion.utils.ControllerUtils;
import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {

    @GetMapping({ "/", "/home", "/index" })
    public String home(Model model, @AuthenticationPrincipal User user) {
        // User user = (User) session.getAttribute("user");

        ControllerUtils.modelCommonAttribute(model, user, "home", "Home");
        return "index";
    }

    // Temp 2nd page
    @GetMapping({ "/page2" })
    public String page2(Model model, @AuthenticationPrincipal User user) {
        ControllerUtils.modelCommonAttribute(model, user, "page2", "Page 2");
        return "page2";
    }
}