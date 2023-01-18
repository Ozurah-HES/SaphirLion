package ch.hearc.SaphirLion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ch.hearc.SaphirLion.model.UserMedia;
import ch.hearc.SaphirLion.repository.UserMediaRepository;
import ch.hearc.SaphirLion.service.UserMediaService_I;

@Service
public class UserMediaService implements UserMediaService_I {
    @Autowired
    private UserMediaRepository userMediaRepository;

    // TODO : Create and update are the same, merge them :)
    @Override
    public UserMedia create(UserMedia userMedia) {
        return userMediaRepository.save(userMedia);
    }

    @Override
    public UserMedia read(Long id) {
        return userMediaRepository.findById(id).orElse(null);
    }

    @Override
    public UserMedia update(UserMedia userMedia) {
        return userMediaRepository.save(userMedia);
    }

    @Override
    public void delete(Long id) {
        userMediaRepository.deleteById(id);
    }

    @Override
    public Page<UserMedia> readAllOfUser(Long userId, Pageable pageable) {
        return userMediaRepository.findByUserId(userId, pageable);
    }

}
