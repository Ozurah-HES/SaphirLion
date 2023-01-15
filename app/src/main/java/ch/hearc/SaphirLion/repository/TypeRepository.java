package ch.hearc.SaphirLion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ch.hearc.SaphirLion.model.Media;
import ch.hearc.SaphirLion.model.Type;

public interface TypeRepository extends CrudRepository<Type, Long> {
    @Query("SELECT t FROM Type t JOIN FETCH t.medias")
    List<Type> findAllWithMedias();

    @Query("SELECT m FROM Media m JOIN FETCH m.type WHERE m.type.id = ?1")
    List<Media> findAllMedias(Long typeId);
}
