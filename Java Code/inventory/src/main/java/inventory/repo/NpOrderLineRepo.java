package inventory.repo;

import inventory.model.*;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface NpOrderLineRepo extends CrudRepository<NpOrderLine, Integer> {
	
	List<NpOrderLine> findByOrderID(int orderID) ;

}
