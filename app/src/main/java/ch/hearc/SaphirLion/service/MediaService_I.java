package ch.hearc.SaphirLion.service;

import java.util.Set;

import ch.hearc.SaphirLion.model.Category;
import ch.hearc.SaphirLion.model.Media;
import ch.hearc.SaphirLion.model.Type;

public interface MediaService_I {
    // CRUD
    public Media create(Media media);
    public Media read(Long id);
    public Media update(Media media);
    public void delete(Long id);

    // Other
    public Set<Media> readAll();
    public Set<Media> readAllOfUser(Long userId);

    // Linked data
    public Set<Type> readAllTypes();
    public Set<Category> readAllCategories();
}
