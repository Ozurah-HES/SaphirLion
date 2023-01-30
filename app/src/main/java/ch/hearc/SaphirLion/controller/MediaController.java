package ch.hearc.SaphirLion.controller;

import java.util.Arrays;
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
import org.springframework.validation.BeanPropertyBindingResult;
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
import ch.hearc.SaphirLion.validator.BelongConnectedValidator;
import jakarta.validation.Valid;

@Controller
public class MediaController {

    @Autowired
    private UserMediaService userMediaService;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private Validator validator;

    @Autowired
    BelongConnectedValidator belongValidator;

    @GetMapping({ "/media" })
    public String index(Model model, @AuthenticationPrincipal User user,
            @RequestParam("page") Optional<Integer> page) {

        int currentPage = page.orElse(1);
        int pageSize = 10;

        if (currentPage < 1) {
            currentPage = 1;
        }

        ControllerUtils.modelCommonAttribute(model, user, "media", "Ma bibliothèque");

        // === User medias stats ===

        // page set to null to retrieve all the user's medias
        List<UserMedia> umList = userMediaService.readAllOfUser(user.getId(), null).getContent();

        Long finished = umList.stream().filter(um -> um.getLastSeen() == um.getNbPublished()).count();
        Long viewed = umList.stream().mapToLong(um -> um.getLastSeen()).sum();
        Long notViewed = umList.stream().mapToLong(um -> um.getNbPublished()).sum() - viewed;
        Long buyed = umList.stream().mapToLong(um -> um.getNbOwned()).sum();
        Long notBuyed = umList.stream().mapToLong(um -> um.getNbPublished()).sum() - buyed;

        model.addAttribute("nbMedia", umList.size());
        model.addAttribute("nbMediaFinished", finished);
        model.addAttribute("nbMediaViewed", viewed);
        model.addAttribute("nbMediaNotViewed", notViewed);
        model.addAttribute("nbMediaBuyed", buyed);
        model.addAttribute("nbMediaNotBuyed", notBuyed);

        // === Pagination for the view ===
        Page<UserMedia> umPage = userMediaService.readAllOfUser(
                user.getId(),
                PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("myMedias", umPage.getContent());
        model.addAttribute("pages", umPage);

        int totalPages = umPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "media";
    }

    @GetMapping({ "/media/edit/{id}" })
    public String edit(
            RedirectAttributes redirectAttrs, Model model, @AuthenticationPrincipal User user,
            @PathVariable(required = true) Integer id) {
        ControllerUtils.modelCommonAttribute(model, user, "media edit", "Modification d'un média");

        UserMedia um;
        // Retrieve the media
        if (model.containsAttribute("myMedia")) { // If back due to validation error, keep previous state
            um = (UserMedia) model.getAttribute("myMedia");
        } else { // get the specified media from the URI
            um = userMediaService.read(id.longValue());
            model.addAttribute("myMedia", um);
        }

        if (!model.containsAttribute("selectedMedia")) {
            model.addAttribute("selectedMedia", um != null ? um.getMedia() : new Media());
        }

        // Validation
        BindingResult belongErrors = new BeanPropertyBindingResult(um, "UserMedia");
        belongValidator.validate(um, belongErrors);
        if (belongErrors.hasErrors()) {
            redirectAttrs.addFlashAttribute("errors", belongErrors.getAllErrors());

            return "redirect:/media";
        }

        // get all unowned medias + the current setted one
        List<Media> medias = mediaService.readSortedAllUnowned(user.getId(), um != null ? Arrays.asList(um.getMedia()) : null);

        // Set the view
        model.addAttribute("medias", medias);
        model.addAttribute("errors", model.asMap().get("errors"));
        return "media.edit";
    }

    @GetMapping({ "/media/add" })
    public String add(Model model, @AuthenticationPrincipal User user) {
        ControllerUtils.modelCommonAttribute(model, user, "media add", "Ajout d'un média");

        List<Media> medias = mediaService.readSortedAllUnowned(user.getId(), null);

        // Retrieve the media
        if (!model.containsAttribute("myMedia")) { // If back due to validation error, keep previous
            model.addAttribute("myMedia", new UserMedia());
        }
        if (!model.containsAttribute("selectedMedia")) {
            model.addAttribute("selectedMedia", medias.size() > 0 ? medias.get(0) : null);
        }

        // Set the view
        model.addAttribute("medias", medias);
        model.addAttribute("errors", model.asMap().get("errors"));
        return "media.edit";
    }

    @PutMapping({ "/media/edit" })
    public String edit(@Valid @ModelAttribute UserMedia userMedia, BindingResult errors,
            RedirectAttributes redirectAttrs, Model model,
            @AuthenticationPrincipal User user, @RequestParam String mediaName) {

        String trimmedMediaName = mediaName.trim();

        userMedia.setUser(user);

        boolean isEdit = userMedia.getId() != null;
        Media m = mediaService.readAll().stream()
                .filter(media -> media.getName().equals(trimmedMediaName))
                .findFirst()
                .orElse(null);

        boolean isNewMedia = m == null;

        if (isNewMedia) {
            m = new Media();
            m.setName(trimmedMediaName);

            // TODO (for next version) : add category and type choice possibility
            m.setCategory(mediaService.readAllCategories().get(0));
            m.setType(mediaService.readAllTypes().get(0));
        }

        userMedia.setMedia(m);

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

        if (isNewMedia) {
            mediaService.save(m);
        }

        // Link objects to userMedia
        userMedia.setMedia(m);

        userMediaService.save(userMedia);

        return "redirect:/media";
    }

    @DeleteMapping({ "/media/delete/{id}" })
    public String delete(@ModelAttribute UserMedia userMedia, BindingResult errors,
            RedirectAttributes redirectAttrs, @AuthenticationPrincipal User user,
            @PathVariable(required = true) Integer id) {
        userMedia.setId(id.longValue());
        userMedia.setUser(user);

        belongValidator.validate(userMedia, errors);
        if (errors.hasErrors()) {
            // TODO : For now, we assume the only possible error here is that the userMedia
            // does not belong to the user
            redirectAttrs.addFlashAttribute("errors", errors.getAllErrors());

            return "redirect:/media";
        }

        userMediaService.delete(userMedia.getId());
        return "redirect:/media";
    }
}