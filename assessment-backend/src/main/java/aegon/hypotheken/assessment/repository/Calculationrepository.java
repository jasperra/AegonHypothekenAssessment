package aegon.hypotheken.assessment.repository;

import aegon.hypotheken.assessment.model.Calculation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Calculationrepository extends CrudRepository<Calculation, Integer> {
}
