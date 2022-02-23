package self.EcommerceApp.EcommerceJenkins.model.persistence.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import self.EcommerceApp.EcommerceJenkins.model.persistence.User;
import self.EcommerceApp.EcommerceJenkins.model.persistence.UserOrder;

import java.util.List;

public interface OrderRepository extends JpaRepository<UserOrder, Long> {
	List<UserOrder> findByUser(User user);
}
