package ch.hearc.SaphirLion.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import ch.hearc.SaphirLion.model.UserMedia;

public interface UserMediaRepository extends CrudRepository<UserMedia, Long>,
        PagingAndSortingRepository<UserMedia, Long> {

    @Query("SELECT um FROM UserMedia um WHERE um.user.id = ?1")
    public Page<UserMedia> findByUserId(Long userId, Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(um) > 0 THEN true ELSE false END FROM UserMedia um WHERE um.user.id = ?1 AND um.id = ?2")
    public boolean belongsToUser(Long userId, Long userMediaId);
}
