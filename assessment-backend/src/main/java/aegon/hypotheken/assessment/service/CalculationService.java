package aegon.hypotheken.assessment.service;

import aegon.hypotheken.assessment.model.Calculation;
import aegon.hypotheken.assessment.model.Operator;
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

    private double add(final double i1, final double i2) {
        return i1 + i2;
    }

    private double subtract(final double i1, final double i2) {
        return i1 - i2;
    }

    private double multiply(final double i1, final double i2) {
        return i1 * i2;
    }

    private double divide(final double i1, final double i2) {
        return i1 / i2;
    }

    public double calculate(final double i1, final Operator operator, final double i2) {
        return switch (operator) {
            case ADD -> add(i1, i2);
            case SUBTRACT -> subtract(i1, i2);
            case MULTIPLY -> multiply(i1, i2);
            case DIVIDE -> divide(i1, i2);
        };
    }

    public List<Calculation> getAllCalculations() {
        log.info("Retrieving all calculations");

        return StreamSupport.stream(
                calculationrepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public void storeCalculation(final String calculation) {
        log.info("Storing new calculation: {}", calculation);

        calculationrepository.save(Calculation.builder().calculation(calculation).build());
    }
}
