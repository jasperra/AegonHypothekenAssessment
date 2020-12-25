package aegon.hypotheken.assessment.service;

import aegon.hypotheken.assessment.model.Calculation;
import aegon.hypotheken.assessment.model.Operator;
import aegon.hypotheken.assessment.repository.Calculationrepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CalculationService.class})
public class CalculationServiceTest {

    @MockBean
    private Calculationrepository calculationrepository;

    @Autowired
    private CalculationService calculationService;

    @Test
    public void addTwoNumbers() {
        double result = calculationService.calculate(1, Operator.ADD, 1);

        assertEquals(2, result);
    }

    @Test
    public void addTwoNegativeNumbers() {
        double result = calculationService.calculate(-1, Operator.ADD, -1);

        assertEquals(-2, result);
    }

    @Test
    public void subtractTwoNumbers() {
        double result = calculationService.calculate(1, Operator.SUBTRACT, 2);

        assertEquals(-1, result);
    }

    @Test
    public void subtractTwoNegativeNumbers() {
        double result = calculationService.calculate(-1, Operator.SUBTRACT, -2);

        assertEquals(1, result);
    }

    @Test
    public void multiplyTwoNumbers() {
        double result = calculationService.calculate(2, Operator.MULTIPLY, 2);

        assertEquals(4, result);
    }

    @Test
    public void multiplyTwoNegativeNumbers() {
        double result = calculationService.calculate(-3, Operator.MULTIPLY, -3);

        assertEquals(9, result);
    }

    @Test
    public void divideTwoNumbers() {
        double result = calculationService.calculate(10, Operator.DIVIDE, 2);

        assertEquals(5, result);
    }

    @Test
    public void divideTwoNegativeNumbers() {
        double result = calculationService.calculate(-30, Operator.DIVIDE, -3);

        assertEquals(10, result);
    }

    @Test
    public void divideByZero() {
        double result = calculationService.calculate(10, Operator.DIVIDE, 0);

        assertEquals(Double.POSITIVE_INFINITY, result);
    }

    @Test
    public void divideZeroBy() {
        double result = calculationService.calculate(0, Operator.DIVIDE, 3);

        assertEquals(0, result);
    }

    @Test
    public void storeCalculation() {
        when(calculationrepository.save(any(Calculation.class))).thenReturn(null);

        calculationService.storeCalculation("");

        verify(calculationrepository, times(1)).save(any(Calculation.class));
    }
}
