package ch.hearc.SaphirLion.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.hearc.SaphirLion.model.User;
import ch.hearc.SaphirLion.model.UserMedia;
import ch.hearc.SaphirLion.service.impl.UserMediaService;
import ch.hearc.SaphirLion.utils.ControllerUtils;

@Controller
public class MediaController {

    @Autowired
    private UserMediaService userMediaService;

    @GetMapping({ "/media" })
    public String home(Model model, @AuthenticationPrincipal User user,
            @RequestParam("page") Optional<Integer> page) {

        int currentPage = page.orElse(1);
        int pageSize = 5;

        if (currentPage < 1) {
            currentPage = 1;
        }

        ControllerUtils.modelCommonAttribute(model, user, "media", "Ma bibliothèque");

        model.addAttribute("nbMedia", 111);
        model.addAttribute("nbMediaFinished", 222);
        model.addAttribute("nbMediaViewed", 333);
        model.addAttribute("nbMediaNotViewed", 444);
        model.addAttribute("nbMediaBuyed", 555);
        model.addAttribute("nbMediaNotBuyed", 666);

        Page<UserMedia> um = userMediaService.readAllOfUser(
                user.getId(),
                PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("myMedias", um.getContent());
        model.addAttribute("pages", um);

        int totalPages = um.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "media";
    }
}