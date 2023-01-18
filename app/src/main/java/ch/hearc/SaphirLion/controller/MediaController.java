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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.hearc.SaphirLion.model.Media;
import ch.hearc.SaphirLion.model.User;
import ch.hearc.SaphirLion.model.UserMedia;
import ch.hearc.SaphirLion.service.impl.MediaService;
import ch.hearc.SaphirLion.service.impl.UserMediaService;
import ch.hearc.SaphirLion.utils.ControllerUtils;

@Controller
public class MediaController {

    @Autowired
    private UserMediaService userMediaService;

    @Autowired
    private MediaService mediaService;

    @GetMapping({ "/media" })
    public String index(Model model, @AuthenticationPrincipal User user,
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

    @GetMapping({ "/media/edit/{id}" })
    public String edit(Model model, @AuthenticationPrincipal User user, @PathVariable(required = true) Integer id) {
        ControllerUtils.modelCommonAttribute(model, user, "media edit", "edit");
        UserMedia um = userMediaService.read(id.longValue());
        List<Media> medias = mediaService.readAll();

        // TODO : Vérifier que le média apparien à l'utilisateur en cas d'édit

        model.addAttribute("myMedia", um);
        model.addAttribute("selectedMedia", um.getMedia());
        model.addAttribute("medias", medias);
        return "media.edit";
    }

    @GetMapping({ "/media/add" })
    public String add(Model model, @AuthenticationPrincipal User user) {
        ControllerUtils.modelCommonAttribute(model, user, "media add", "add");

        List<Media> medias = mediaService.readAll();

        model.addAttribute("myMedia", new UserMedia());
        model.addAttribute("selectedMedia", medias.size() > 0 ? medias.get(0) : null);
        model.addAttribute("medias", medias);
        return "media.edit";
    }

    @PostMapping({ "/media/edit", "/tmp3" })
    public String edit(@ModelAttribute UserMedia userMedia, BindingResult errors, Model model,
            @AuthenticationPrincipal User user, @RequestParam String mediaName) {
        System.out.println("mediaName: " + mediaName);
        System.out.println("userMedia: " + userMedia);

        // TODO : Vérifier que le média apparien à l'utilisateur en cas d'édit

        Media m = mediaService.readAll().stream()
                .filter(media -> media.getName().equals(mediaName))
                .findFirst()
                .orElse(null);

        if (m == null) {
            // TODO : Add category + type in form
            // TODO Add security if category or type not exist
            m = new Media();
            m.setName(mediaName);
            m.setCategory(mediaService.readAllCategories().get(0));
            m.setType(mediaService.readAllTypes().get(0));
            mediaService.create(m);
        }
        // TODO remplace to service "findByName"
        userMedia.setMedia(m);
        userMedia.setUser(user);

        userMediaService.update(userMedia);

        return "redirect:/media";
    }
}