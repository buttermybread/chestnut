package inventory.repo;

import inventory.model.*;

import org.springframework.data.repository.CrudRepository;

public interface SupplierRepo extends CrudRepository<Supplier, Integer> {
	
	Supplier findByName(String name) ;

}

