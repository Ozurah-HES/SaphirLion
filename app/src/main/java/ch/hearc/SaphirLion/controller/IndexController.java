package ch.hearc.SaphirLion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ch.hearc.SaphirLion.model.User;
import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {

    @GetMapping({"/","/home", "/index"})
    public String home(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");

        model.addAttribute("currentPageName", "home");
        model.addAttribute("title", "Home");
        model.addAttribute("user", user);
        return "index";
    }

    // Temp 2nd page
    @GetMapping({"/login"})
    public String login(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");

        model.addAttribute("currentPageName", "login");
        model.addAttribute("title", "Login");
        model.addAttribute("user", user);
        return "login";
    }
}