package ch.hearc.SaphirLion.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ch.hearc.SaphirLion.model.User;
import ch.hearc.SaphirLion.model.UserMedia;

public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT u FROM User u JOIN FETCH u.usermedias")
    Set<User> findAllWithUserMedias();

    @Query("SELECT um FROM UserMedia um JOIN FETCH um.user WHERE um.user.id = ?1")
    Set<UserMedia> findAllUserMedia(Long userId);

    User findByUsername(String username);
}
