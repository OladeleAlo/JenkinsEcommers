package self.EcommerceApp.EcommerceJenkins.controllers;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import self.EcommerceApp.EcommerceJenkins.model.persistence.Cart;
import self.EcommerceApp.EcommerceJenkins.model.persistence.Item;
import self.EcommerceApp.EcommerceJenkins.model.persistence.User;
import self.EcommerceApp.EcommerceJenkins.model.persistence.repositories.CartRepository;
import self.EcommerceApp.EcommerceJenkins.model.persistence.repositories.ItemRepository;
import self.EcommerceApp.EcommerceJenkins.model.persistence.repositories.UserRepository;
import self.EcommerceApp.EcommerceJenkins.model.requests.ModifyCartRequest;

import java.util.Optional;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	private static final Logger log = Logger.getLogger(CartController.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ItemRepository itemRepository;

	@PostMapping("/addToCart")
	public ResponseEntity<Cart> addTocart(@RequestBody ModifyCartRequest request) {
		User user = userRepository.findByUsername(request.getUsername());
		if (user == null) {
			log.error("Username not found: " + request.getUsername());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Optional<Item> item = itemRepository.findById(request.getItemId());
		if (!item.isPresent()) {
			log.error("Item not found: " + request.getItemId());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Cart cart = user.getCart();
		IntStream.range(0, request.getQuantity())
				.forEach(i -> cart.addItem(item.get()));
		cartRepository.save(cart);
		return ResponseEntity.ok(cart);
	}

	@PostMapping("/removeFromCart")
	public ResponseEntity<Cart> removeFromcart(@RequestBody ModifyCartRequest request) {
		User user = userRepository.findByUsername(request.getUsername());
		if (user == null) {
			log.error("Username not found: " + request.getUsername());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Optional<Item> item = itemRepository.findById(request.getItemId());
		if (!item.isPresent()) {
			log.error("Item not found: " + request.getItemId());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Cart cart = user.getCart();
		IntStream.range(0, request.getQuantity())
				.forEach(i -> cart.removeItem(item.get()));
		cartRepository.save(cart);
		if (log.isDebugEnabled())
			log.debug("Item successfully removed from cart: " + item.toString());
		return ResponseEntity.ok(cart);
	}
		
}
