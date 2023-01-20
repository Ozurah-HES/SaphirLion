package ch.hearc.SaphirLion.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ch.hearc.SaphirLion.model.Media;
import ch.hearc.SaphirLion.model.User;
import ch.hearc.SaphirLion.model.UserMedia;
import ch.hearc.SaphirLion.service.impl.MediaService;
import ch.hearc.SaphirLion.service.impl.UserMediaService;
import ch.hearc.SaphirLion.utils.ControllerUtils;
import ch.hearc.SaphirLion.validator.BelongValidator;
import jakarta.validation.Valid;


@Controller
public class MediaController {

    @Autowired
    private UserMediaService userMediaService;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private Validator validator;

    @Autowired BelongValidator belongValidator;

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
        ControllerUtils.modelCommonAttribute(model, user, "media edit", "Modification d'un média");

        List<Media> medias = mediaService.readAll();

        UserMedia um;
        if (model.containsAttribute("myMedia")) { // If back due to validation error, keep previous state
            um = (UserMedia) model.getAttribute("myMedia");
        } else {
            um = userMediaService.read(id.longValue());
            model.addAttribute("myMedia", um);
        }

        if (!model.containsAttribute("selectedMedia")) {
            model.addAttribute("selectedMedia", um.getMedia());
        }

        // TODO : Vérifier que le média apparien à l'utilisateur en cas d'édit

        model.addAttribute("medias", medias);
        model.addAttribute("errors", model.asMap().get("errors"));
        return "media.edit";
    }

    @GetMapping({ "/media/add" })
    public String add(Model model, @AuthenticationPrincipal User user) {
        ControllerUtils.modelCommonAttribute(model, user, "media add", "Ajout d'un média");

        List<Media> medias = mediaService.readAll();

        if (!model.containsAttribute("myMedia")) { // If back due to validation error, keep previous
            model.addAttribute("myMedia", new UserMedia());
        }
        if (!model.containsAttribute("selectedMedia")) {
            model.addAttribute("selectedMedia", medias.size() > 0 ? medias.get(0) : null);
        }

        model.addAttribute("medias", medias);
        model.addAttribute("errors", model.asMap().get("errors"));
        return "media.edit";
    }

    @PutMapping({ "/media/edit" })
    public String edit(@Valid @ModelAttribute UserMedia userMedia, BindingResult errors,
            RedirectAttributes redirectAttrs, Model model,
            @AuthenticationPrincipal User user, @RequestParam String mediaName) {

        userMedia.setUser(user);

        boolean isEdit = userMedia.getId() != null;
        Media m = mediaService.readAll().stream()
                .filter(media -> media.getName().equals(mediaName))
                .findFirst()
                .orElse(null);

        boolean isNewMedia = m == null;

        if (isNewMedia) {
            m = new Media();
            m.setName(mediaName);

            // TODO (for next version) : add category and type choice possibility
            m.setCategory(mediaService.readAllCategories().get(0));
            m.setType(mediaService.readAllTypes().get(0));
        }

        // Validation
        validator.validate(m, errors);
        belongValidator.validate(userMedia, errors);
        if (errors.hasErrors()) {
            // flash attributes transmit is attributes to the model after the redirect
            redirectAttrs.addFlashAttribute("errors", errors.getAllErrors());
            redirectAttrs.addFlashAttribute("myMedia", userMedia);
            redirectAttrs.addFlashAttribute("selectedMedia", m);

            if (isEdit)
                return "redirect:/media/edit/" + userMedia.getId();
            else
                return "redirect:/media/add";
        }

        // TODO : Vérifier que le média apparient à l'utilisateur en cas d'édit

        if (isNewMedia) {
            mediaService.save(m);
        }

        // Link objects to userMedia
        userMedia.setMedia(m);

        userMediaService.save(userMedia);

        return "redirect:/media";
    }

    @DeleteMapping({ "/media/delete" })
    public String delete(@ModelAttribute UserMedia userMedia) {
        // TODO : Vérifier que le média apparient à l'utilisateur
        userMediaService.delete(userMedia.getId());
        return "redirect:/media";
    }
}