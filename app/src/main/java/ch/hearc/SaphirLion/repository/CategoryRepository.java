package ch.hearc.SaphirLion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ch.hearc.SaphirLion.model.Category;
import ch.hearc.SaphirLion.model.Media;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    @Query("SELECT c FROM Category c JOIN FETCH c.medias")
    List<Category> findAllWithMedias();

    @Query("SELECT m FROM Media m JOIN FETCH m.category WHERE m.category.id = ?1")
    List<Media> findAllMedias(Long categoryId);
}
