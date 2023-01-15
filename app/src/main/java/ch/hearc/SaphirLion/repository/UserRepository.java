package ch.hearc.SaphirLion.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ch.hearc.SaphirLion.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
