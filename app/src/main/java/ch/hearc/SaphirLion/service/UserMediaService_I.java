package ch.hearc.SaphirLion.service;

import java.util.Set;

import ch.hearc.SaphirLion.model.UserMedia;

public interface UserMediaService_I {
    // CRUD
    public UserMedia create(UserMedia userMedia);
    public UserMedia read(Long id);
    public UserMedia update(UserMedia userMedia);
    public void delete(Long id);

    // Other
    public Set<UserMedia> readAllOfUser(Long userId);
}
