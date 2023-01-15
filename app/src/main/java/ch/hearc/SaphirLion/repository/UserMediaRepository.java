package ch.hearc.SaphirLion.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ch.hearc.SaphirLion.model.UserMedia;

public interface UserMediaRepository extends CrudRepository<UserMedia, Long> {
    
    // no need of @Query, Spring understand we want "SELECT * FROM user_media WHERE user_id = ?1"
    public List<UserMedia> findByUserId(Long userId);
}
