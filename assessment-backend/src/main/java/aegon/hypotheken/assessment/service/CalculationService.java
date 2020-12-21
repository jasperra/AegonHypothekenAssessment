package aegon.hypotheken.assessment.service;

import aegon.hypotheken.assessment.model.Calculation;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CalculationService {

    public double add(int i1, int i2) {
        return i1 + i2;
    }

    public double subtract(int i1, int i2) {
        return i1 - i2;
    }

    public double multiply(int i1, int i2) {
        return i1 * i2;
    }

    public double divide(int i1, int i2) {
        return i1 / i2;
    }

    public List<Calculation> getAllCalculations() {
        return Collections.emptyList();
    }
}
