package inventory.repo;

import inventory.model.*;

import java.time.LocalDate;
import org.springframework.data.repository.CrudRepository;

public interface NpOrderRepo extends CrudRepository<NpOrder, Integer> {
	
	NpOrder findByDate(LocalDate date) ;

}
