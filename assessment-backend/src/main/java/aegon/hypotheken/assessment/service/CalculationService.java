package aegon.hypotheken.assessment.service;

import aegon.hypotheken.assessment.model.Calculation;
import aegon.hypotheken.assessment.repository.Calculationrepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class CalculationService {

    private final Calculationrepository calculationrepository;

    @Autowired
    public CalculationService(final Calculationrepository calculationrepository) {
        this.calculationrepository = calculationrepository;
    }

    public double add(final int i1, final int i2) {
        final double result = i1 + i2;

        storeCalculation(String.format("%s + %s = %s", i1, i2, result));

        return result;
    }

    public double subtract(final int i1, final int i2) {
        final double result = i1 - i2;

        storeCalculation(String.format("%s - %s = %s", i1, i2, result));

        return result;
    }

    public double multiply(final int i1, final int i2) {
        final double result = i1 * i2;

        storeCalculation(String.format("%s * %s = %s", i1, i2, result));

        return result;
    }

    public double divide(final int i1, final int i2) {
        final double result = i1 / i2;

        storeCalculation(String.format("%s / %s = %s", i1, i2, result));

        return result;
    }

    public List<Calculation> getAllCalculations() {
        log.info("Retrieving all calculations");

        return StreamSupport.stream(
                calculationrepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    private void storeCalculation(final String calculation) {
        log.info("Storing new calculation: {}", calculation);

        calculationrepository.save(Calculation.builder().calculation(calculation).build());
    }
}
