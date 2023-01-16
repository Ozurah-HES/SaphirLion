package ch.hearc.SaphirLion.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ch.hearc.SaphirLion.model.User;
import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {

    @GetMapping({ "/", "/home", "/index" })
    public String home(Model model, HttpSession session, @AuthenticationPrincipal User user) {
        // User user = (User) session.getAttribute("user");

        model.addAttribute("currentPageName", "home");
        model.addAttribute("title", "Home");
        model.addAttribute("user", user);
        return "index";
    }

    // Temp 2nd page
    @GetMapping({ "/page2" })
    public String page2(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");

        model.addAttribute("currentPageName", "page2");
        model.addAttribute("title", "page2");
        model.addAttribute("user", user);
        return "page2";
    }
}