package ch.hearc.SaphirLion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hearc.SaphirLion.model.UserMedia;
import ch.hearc.SaphirLion.repository.UserMediaRepository;
import ch.hearc.SaphirLion.service.UserMediaService_I;

@Service
public class UserMediaService implements UserMediaService_I {
    @Autowired
    private UserMediaRepository userMediaRepository;

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
    public List<UserMedia> readAllOfUser(Long userId) {
       return userMediaRepository.findByUserId(userId);
    }

}
