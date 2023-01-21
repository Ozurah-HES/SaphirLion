package ch.hearc.SaphirLion.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.hearc.SaphirLion.model.User;
import ch.hearc.SaphirLion.utils.ControllerUtils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

// https://www.baeldung.com/spring-boot-custom-error-page

@Controller
public class MyErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(Model model, HttpServletRequest request, @AuthenticationPrincipal User user) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        var title = "Erreur, status inconnu";
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            title = "Erreur " + statusCode;
            model.addAttribute("code", statusCode);
        }

        ControllerUtils.modelCommonAttribute(model, user, "error", title);

        return "error.http";
    }
}
