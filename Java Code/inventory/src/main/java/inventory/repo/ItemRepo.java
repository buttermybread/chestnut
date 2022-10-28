package inventory.repo;

import inventory.model.*;

import org.springframework.data.repository.CrudRepository;

public interface ItemRepo extends CrudRepository<Item, Integer> {

	Item findByName(String name) ;

}
