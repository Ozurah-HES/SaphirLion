package ch.hearc.SaphirLion.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ch.hearc.SaphirLion.model.Media;
import ch.hearc.SaphirLion.model.Type;

public interface TypeRepository extends CrudRepository<Type, Long> {
    @Query("SELECT t FROM Type t JOIN FETCH t.medias")
    Set<Type> findAllWithMedias();

    @Query("SELECT m FROM Media m JOIN FETCH m.type WHERE m.type.id = ?1")
    Set<Media> findAllMedias(Long typeId);
}
