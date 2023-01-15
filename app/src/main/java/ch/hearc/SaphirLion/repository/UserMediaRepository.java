package ch.hearc.SaphirLion.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import ch.hearc.SaphirLion.model.UserMedia;

public interface UserMediaRepository extends CrudRepository<UserMedia, Long> {

    public Set<UserMedia> findByUserId(Long userId);
}
