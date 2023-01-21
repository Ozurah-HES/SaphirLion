package ch.hearc.SaphirLion.service;

import java.util.Collection;
import java.util.List;

import ch.hearc.SaphirLion.model.Category;
import ch.hearc.SaphirLion.model.Media;
import ch.hearc.SaphirLion.model.Type;

public interface MediaService_I {
    // CRUD (Create & Update are the same --> save)
    public Media save(Media media);

    public Media read(Long id);

    public void delete(Long id);

    // Other
    public List<Media> readAll();

    public List<Media> readAllOfUser(Long userId);

    public List<Media> readSortedAllUnowned(Long userId, Collection<Media> except);

    // Linked data
    public List<Type> readAllTypes();

    public List<Category> readAllCategories();
}
