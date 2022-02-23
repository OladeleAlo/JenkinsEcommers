package self.EcommerceApp.EcommerceJenkins.model.persistence.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import self.EcommerceApp.EcommerceJenkins.model.persistence.User;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
