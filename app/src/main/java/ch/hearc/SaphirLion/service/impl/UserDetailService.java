package ch.hearc.SaphirLion.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ch.hearc.SaphirLion.model.User;
import ch.hearc.SaphirLion.repository.UserRepository;

/**
 * Service to load a user from the database, used by Spring Security
 */
public class UserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("Could not find user");
		}

		return user;
	}
}
