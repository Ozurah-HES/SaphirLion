package ch.hearc.SaphirLion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ch.hearc.SaphirLion.model.User;
import ch.hearc.SaphirLion.service.impl.UserMediaService;
import ch.hearc.SaphirLion.utils.ControllerUtils;

@Controller
public class MediaController {

    @Autowired
    private UserMediaService userMediaService;

    @GetMapping({ "/media" })
    public String home(Model model, @AuthenticationPrincipal User user) {
        ControllerUtils.modelCommonAttribute(model, user, "media", "Ma bibliothèque");

        model.addAttribute("nbMedia", 111);
        model.addAttribute("nbMediaFinished", 222);
        model.addAttribute("nbMediaViewed", 333);
        model.addAttribute("nbMediaNotViewed", 444);
        model.addAttribute("nbMediaBuyed", 555);
        model.addAttribute("nbMediaNotBuyed", 666);

        model.addAttribute("myMedias", userMediaService.readAllOfUser(user.getId()));
        return "media";
    }
}