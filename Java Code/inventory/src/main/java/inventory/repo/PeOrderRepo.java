package inventory.repo;

import inventory.model.*;

import java.time.LocalDate;
import org.springframework.data.repository.CrudRepository;

public interface PeOrderRepo extends CrudRepository<PeOrder, Integer> {
	
	PeOrder findByDate(LocalDate date) ;

}
