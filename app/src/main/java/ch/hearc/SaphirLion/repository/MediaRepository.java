package ch.hearc.SaphirLion.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ch.hearc.SaphirLion.model.Media;
import ch.hearc.SaphirLion.model.UserMedia;

public interface MediaRepository extends CrudRepository<Media, Long> {
    @Query("SELECT m FROM Media m JOIN FETCH m.usermedias")
    Set<Media> findAllWithUserMedias();

    @Query("SELECT um FROM UserMedia um JOIN FETCH um.media WHERE um.media.id = ?1")
    Set<UserMedia> findAllUserMedia(Long mediaId);

    @Query("SELECT m FROM Media m JOIN FETCH m.usermedias")
    // @Query("SELECT m FROM Media m JOIN FETCH m.usermedias WHERE m.usermedias.user.id = ?1") TODO
    Set<Media> findAllOfUser(Long userId);
}
