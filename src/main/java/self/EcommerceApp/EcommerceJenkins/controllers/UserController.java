package self.EcommerceApp.EcommerceJenkins.controllers;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import self.EcommerceApp.EcommerceJenkins.model.persistence.Cart;
import self.EcommerceApp.EcommerceJenkins.model.persistence.User;
import self.EcommerceApp.EcommerceJenkins.model.persistence.repositories.CartRepository;
import self.EcommerceApp.EcommerceJenkins.model.persistence.repositories.UserRepository;
import self.EcommerceApp.EcommerceJenkins.model.requests.CreateUserRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private static final Logger log = Logger.getLogger(UserController.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/id/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		return ResponseEntity.of(userRepository.findById(id));
	}

	@GetMapping("/{username}")
	public ResponseEntity<User> findByUserName(@PathVariable String username) {
		User user = userRepository.findByUsername(username);
		return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
	}

	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
		log.info("Entering Create User method.");
		User user = new User();
		user.setUsername(createUserRequest.getUsername());
		log.info("Username set to " + createUserRequest.getUsername());
		Cart cart = new Cart();
		cartRepository.save(cart);
		if (log.isDebugEnabled())
			log.debug("Cart for user " + user.getUsername() + " was saved to the repository.");
		user.setCart(cart);
		if (createUserRequest.getPassword().length() < 7 ||
				!createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())) {
			log.error("Error with user password. Cannot create user " + createUserRequest.getUsername());
			return ResponseEntity.badRequest().build();
		}
		user.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));
		if (log.isDebugEnabled())
			log.debug("Encrypting password for " + user.getUsername());
		userRepository.save(user);
		log.info("User " + user.getUsername() + " successfully saved.");
		return ResponseEntity.ok(user);
	}
	
}
