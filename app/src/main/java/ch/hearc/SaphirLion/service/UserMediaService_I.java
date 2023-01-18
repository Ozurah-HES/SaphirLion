package ch.hearc.SaphirLion.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ch.hearc.SaphirLion.model.UserMedia;

public interface UserMediaService_I {
    // CRUD
    public UserMedia create(UserMedia userMedia);

    public UserMedia read(Long id);

    public UserMedia update(UserMedia userMedia);

    public void delete(Long id);

    // Other
    public Page<UserMedia> readAllOfUser(Long userId, Pageable pageable);
}
