package self.EcommerceApp.EcommerceJenkins.controllers;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import self.EcommerceApp.EcommerceJenkins.model.persistence.User;
import self.EcommerceApp.EcommerceJenkins.model.persistence.UserOrder;
import self.EcommerceApp.EcommerceJenkins.model.persistence.repositories.OrderRepository;
import self.EcommerceApp.EcommerceJenkins.model.persistence.repositories.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	private static final Logger log = Logger.getLogger(OrderController.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderRepository orderRepository;


	@PostMapping("/submit/{username}")
	public ResponseEntity<UserOrder> submit(@PathVariable String username) {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			log.error("Username not found: " + username);
			return ResponseEntity.notFound().build();
		}
		UserOrder order = UserOrder.createFromCart(user.getCart());
		orderRepository.save(order);
		log.info(username + " successfully submitted an order: " + order.toString());
		return ResponseEntity.ok(order);
	}

	@GetMapping("/history/{username}")
	public ResponseEntity<List<UserOrder>> getOrdersForUser(@PathVariable String username) {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			log.error("Username not found: " + username);
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(orderRepository.findByUser(user));
	}
}
