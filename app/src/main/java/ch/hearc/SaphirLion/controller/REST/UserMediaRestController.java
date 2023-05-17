package ch.hearc.SaphirLion.controller.REST;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.hearc.SaphirLion.model.User;
import ch.hearc.SaphirLion.model.UserMedia;
import ch.hearc.SaphirLion.service.impl.MediaService;
import ch.hearc.SaphirLion.service.impl.UserMediaService;
import ch.hearc.SaphirLion.validator.BelongUserValidator;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserMediaRestController {

    @Autowired
    private UserMediaService userMediaService;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private Validator validator;

    @Autowired
    private BelongUserValidator belongValidator;

    /*
     * @GetMapping({ "/media" })
     * public String index(Model model, @AuthenticationPrincipal User user,
     * 
     * @RequestParam("page") Optional<Integer> page) {
     * 
     * int currentPage = page.orElse(1);
     * int pageSize = 10;
     * 
     * if (currentPage < 1) {
     * currentPage = 1;
     * }
     * 
     * ControllerUtils.modelCommonAttribute(model, user, "media",
     * "Ma bibliothèque");
     * 
     * // === User medias stats ===
     * 
     * // page set to null to retrieve all the user's medias
     * List<UserMedia> umList = userMediaService.readAllOfUser(user.getId(),
     * null).getContent();
     * 
     * Long finished = umList.stream().filter(um -> um.getLastSeen() ==
     * um.getNbPublished()).count();
     * Long viewed = umList.stream().mapToLong(um -> um.getLastSeen()).sum();
     * Long notViewed = umList.stream().mapToLong(um -> um.getNbPublished()).sum() -
     * viewed;
     * Long buyed = umList.stream().mapToLong(um -> um.getNbOwned()).sum();
     * Long notBuyed = umList.stream().mapToLong(um -> um.getNbPublished()).sum() -
     * buyed;
     * 
     * model.addAttribute("nbMedia", umList.size());
     * model.addAttribute("nbMediaFinished", finished);
     * model.addAttribute("nbMediaViewed", viewed);
     * model.addAttribute("nbMediaNotViewed", notViewed);
     * model.addAttribute("nbMediaBuyed", buyed);
     * model.addAttribute("nbMediaNotBuyed", notBuyed);
     * 
     * // === Pagination for the view ===
     * Page<UserMedia> umPage = userMediaService.readAllOfUser(
     * user.getId(),
     * PageRequest.of(currentPage - 1, pageSize));
     * 
     * model.addAttribute("myMedias", umPage.getContent());
     * model.addAttribute("pages", umPage);
     * 
     * int totalPages = umPage.getTotalPages();
     * if (totalPages > 0) {
     * List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
     * .boxed()
     * .collect(Collectors.toList());
     * model.addAttribute("pageNumbers", pageNumbers);
     * }
     * 
     * return "media";
     * }
     * 
     * @GetMapping({ "/media/edit/{id}" })
     * public String edit(
     * RedirectAttributes redirectAttrs, Model model, @AuthenticationPrincipal User
     * user,
     * 
     * @PathVariable(required = true) Integer id) {
     * ControllerUtils.modelCommonAttribute(model, user, "media edit",
     * "Modification d'un média");
     * 
     * UserMedia um;
     * if (model.containsAttribute("myMedia")) { // If back due to validation error,
     * keep previous state
     * um = (UserMedia) model.getAttribute("myMedia");
     * } else {
     * um = userMediaService.read(id.longValue());
     * model.addAttribute("myMedia", um);
     * }
     * 
     * if (!model.containsAttribute("selectedMedia")) {
     * model.addAttribute("selectedMedia", um.getMedia());
     * }
     * 
     * BindingResult belongErrors = new BeanPropertyBindingResult(um, "UserMedia");
     * belongValidator.validate(um, belongErrors);
     * if (belongErrors.hasErrors()) {
     * redirectAttrs.addFlashAttribute("errors", belongErrors.getAllErrors());
     * 
     * return "redirect:/media";
     * }
     * 
     * List<Media> medias = mediaService.readSortedAllUnowned(user.getId(),
     * Arrays.asList(um.getMedia()));
     * 
     * model.addAttribute("medias", medias);
     * model.addAttribute("errors", model.asMap().get("errors"));
     * return "media.edit";
     * }
     * 
     * @GetMapping({ "/media/add" })
     * public String add(Model model, @AuthenticationPrincipal User user) {
     * ControllerUtils.modelCommonAttribute(model, user, "media add",
     * "Ajout d'un média");
     * 
     * List<Media> medias = mediaService.readSortedAllUnowned(user.getId(), null);
     * 
     * if (!model.containsAttribute("myMedia")) { // If back due to validation
     * error, keep previous
     * model.addAttribute("myMedia", new UserMedia());
     * }
     * if (!model.containsAttribute("selectedMedia")) {
     * model.addAttribute("selectedMedia", medias.size() > 0 ? medias.get(0) :
     * null);
     * }
     * 
     * model.addAttribute("medias", medias);
     * model.addAttribute("errors", model.asMap().get("errors"));
     * return "media.edit";
     * }
     * 
     * @PutMapping({ "/media/edit" })
     * public String edit(@Valid @ModelAttribute UserMedia userMedia, BindingResult
     * errors,
     * RedirectAttributes redirectAttrs, Model model,
     * 
     * @AuthenticationPrincipal User user, @RequestParam String mediaName) {
     * 
     * String trimmedMediaName = mediaName.trim();
     * 
     * userMedia.setUser(user);
     * 
     * boolean isEdit = userMedia.getId() != null;
     * Media m = mediaService.readAll().stream()
     * .filter(media -> media.getName().equals(trimmedMediaName))
     * .findFirst()
     * .orElse(null);
     * 
     * boolean isNewMedia = m == null;
     * 
     * if (isNewMedia) {
     * m = new Media();
     * m.setName(trimmedMediaName);
     * 
     * // TODO (for next version) : add category and type choice possibility
     * m.setCategory(mediaService.readAllCategories().get(0));
     * m.setType(mediaService.readAllTypes().get(0));
     * }
     * 
     * // Validation
     * validator.validate(m, errors);
     * belongValidator.validate(userMedia, errors);
     * if (errors.hasErrors()) {
     * // flash attributes transmit is attributes to the model after the redirect
     * redirectAttrs.addFlashAttribute("errors", errors.getAllErrors());
     * redirectAttrs.addFlashAttribute("myMedia", userMedia);
     * redirectAttrs.addFlashAttribute("selectedMedia", m);
     * 
     * if (isEdit)
     * return "redirect:/media/edit/" + userMedia.getId();
     * else
     * return "redirect:/media/add";
     * }
     * 
     * if (isNewMedia) {
     * mediaService.save(m);
     * }
     * 
     * // Link objects to userMedia
     * userMedia.setMedia(m);
     * 
     * userMediaService.save(userMedia);
     * 
     * return "redirect:/media";
     * }
     */

    /*
     * @DeleteMapping("/media/delete/", consumes = "application/json", produces =
     * "application/json")
     * public String delete(@ModelAttribute UserMedia userMedia, BindingResult
     * errors,
     * RedirectAttributes redirectAttrs, @AuthenticationPrincipal User user,
     * ) {
     * userMedia.setId(id.longValue());
     * userMedia.setUser(user);
     * 
     * belongValidator.validate(userMedia, errors);
     * if (errors.hasErrors()) {
     * // TODO : For now, we assume the only possible error here is that the
     * userMedia
     * // does not belong to the user
     * redirectAttrs.addFlashAttribute("errors", errors.getAllErrors());
     * 
     * return "redirect:/media";
     * }
     * 
     * userMediaService.delete(userMedia.getId());
     * return "redirect:/media";
     * }
     */

    @DeleteMapping(value = "/user/media", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> delete(@RequestBody JsonNode requestBody, BindingResult errors) {
        ObjectMapper objectMapper = new ObjectMapper();
        // Disable fail on unknown properties to be able to retrieve "non property" of UserMedia and Also be able to auto-serialize usermedia
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        
        User u = new User();
        UserMedia userMedia = null;

        try {
            u.setId(requestBody.get("user-id").asLong());
            userMedia = objectMapper.treeToValue(requestBody, UserMedia.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("[\"json parse failed\"]");
        }

        userMedia.setUser(u);

        BindingResult userMediaErrors = new BeanPropertyBindingResult(userMedia, "UserMedia");

        belongValidator.validate(userMedia, userMediaErrors);
        if (errors.hasErrors() || userMediaErrors.hasErrors()) {
            List<String> errorsResponse = new ArrayList<>();
            for (FieldError error : errors.getFieldErrors()) {
                System.out.println(error.getDefaultMessage());
                errorsResponse.add(error.getDefaultMessage());
            }
            for (FieldError error : userMediaErrors.getFieldErrors()) {
                System.out.println(error.getDefaultMessage());
                errorsResponse.add(error.getDefaultMessage());
            }

            String errorsJson = null;
            try {
                errorsJson = objectMapper.writeValueAsString(errorsResponse);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorsJson);
        }

        userMediaService.delete(userMedia.getId());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("[\"deletion done\"]");
    }
}