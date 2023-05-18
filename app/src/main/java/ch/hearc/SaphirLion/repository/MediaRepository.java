package ch.hearc.SaphirLion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ch.hearc.SaphirLion.model.Media;
import ch.hearc.SaphirLion.model.UserMedia;

public interface MediaRepository extends CrudRepository<Media, Long> {
    @Query("SELECT m FROM Media m JOIN FETCH m.usermedias")
    List<Media> findAllWithUserMedias();

    @Query("SELECT um FROM UserMedia um JOIN FETCH um.media WHERE um.media.id = ?1")
    List<UserMedia> findAllUserMedia(Long mediaId);

    @Query("SELECT m FROM Media m JOIN FETCH m.usermedias um WHERE um.user.id = ?1")
    List<Media> findAllOfUser(Long userId);

    Media findByName(String name);
}
