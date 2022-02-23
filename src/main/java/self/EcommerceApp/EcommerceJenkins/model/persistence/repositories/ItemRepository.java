package self.EcommerceApp.EcommerceJenkins.model.persistence.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import self.EcommerceApp.EcommerceJenkins.model.persistence.Item;

import java.util.List;
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
	public List<Item> findByName(String name);

}
