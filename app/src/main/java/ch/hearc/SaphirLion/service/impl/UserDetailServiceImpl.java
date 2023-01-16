// package ch.hearc.SaphirLion.service.impl;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;

// import ch.hearc.SaphirLion.model.User;
// import ch.hearc.SaphirLion.repository.UserRepository;


// public class UserDetailServiceImpl implements UserDetailsService {

// 	@Autowired
// 	private UserRepository userRepository;

// 	public User loadUserByUsername(String username) throws UsernameNotFoundException {
//         System.out.println("HEYO3");
		
// 		User user = userRepository.findByUsername(username);

// 		if (user == null) {
// 			throw new UsernameNotFoundException("Could not find user");
// 		}
		
// 		return user;
//      }
// }