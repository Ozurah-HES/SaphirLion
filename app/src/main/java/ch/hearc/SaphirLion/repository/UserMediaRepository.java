package ch.hearc.SaphirLion.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import ch.hearc.SaphirLion.model.UserMedia;

public interface UserMediaRepository extends CrudRepository<UserMedia, Long>,
        PagingAndSortingRepository<UserMedia, Long> {

    // no need of @Query, Spring understand we want
    // "SELECT * FROM user_media WHERE user_id = ?1"
    @Query("SELECT um FROM UserMedia um WHERE um.user.id = ?1")
    public List<UserMedia> findByUserId(Long userId, Pageable pageable);

    @Query("SELECT um FROM UserMedia um WHERE um.user.id = ?1")
    public Page<UserMedia> findByUserId2(Long userId, Pageable pageable);
}
