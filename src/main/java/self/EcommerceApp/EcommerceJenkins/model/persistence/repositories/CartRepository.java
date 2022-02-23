package self.EcommerceApp.EcommerceJenkins.model.persistence.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import self.EcommerceApp.EcommerceJenkins.model.persistence.Cart;
import self.EcommerceApp.EcommerceJenkins.model.persistence.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	Cart findByUser(User user);
}
