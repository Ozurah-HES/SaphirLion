package ch.hearc.SaphirLion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hearc.SaphirLion.model.Category;
import ch.hearc.SaphirLion.model.Media;
import ch.hearc.SaphirLion.model.Type;
import ch.hearc.SaphirLion.repository.CategoryRepository;
import ch.hearc.SaphirLion.repository.MediaRepository;
import ch.hearc.SaphirLion.repository.TypeRepository;
import ch.hearc.SaphirLion.service.MediaService_I;

@Service
public class MediaService implements MediaService_I {

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Media create(Media media) {
        return mediaRepository.save(media);
    }

    @Override
    public Media read(Long id) {
        return mediaRepository.findById(id).orElse(null);
    }

    @Override
    public Media update(Media media) {
        return mediaRepository.save(media);
    }

    @Override
    public void delete(Long id) {
        mediaRepository.deleteById(id);
    }

    @Override
    public List<Media> readAll() {
        return (List<Media>) mediaRepository.findAll();
    }

    @Override
    public List<Media> readAllOfUser(Long userId) {

        return mediaRepository.findAllOfUser(userId);

        // === OLD TEST CODE (Should be tested when we have seen the correct way to do
        // tests) ===

        // var medias = mediaRepository.findAllWithUserMedias();
        // return medias.stream().filter(
        // media -> media.getUserMedias().stream().anyMatch(
        // userMedia -> userMedia.getUser().getId() == userId
        // )).toList();
    }

    @Override
    public List<Type> readAllTypes() {
        return (List<Type>) typeRepository.findAll();
    }

    @Override
    public List<Category> readAllCategories() {
        return (List<Category>) categoryRepository.findAll();
    }
}
