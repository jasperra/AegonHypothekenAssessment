package aegon.hypotheken.assessment.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CalculationService.class})
public class CalculationServiceTest {

    @Autowired
    private CalculationService calculationService;

    @Test
    public void addTwoNumbers() {
        double result = calculationService.add(1, 1);

        assertEquals(2, result);
    }

    @Test
    public void addTwoNegativeNumbers() {
        double result = calculationService.add(-1, -1);

        assertEquals(-2, result);
    }

    @Test
    public void subtractTwoNumbers() {
        double result = calculationService.subtract(1, 2);

        assertEquals(-1, result);
    }

    @Test
    public void subtractTwoNegativeNumbers() {
        double result = calculationService.subtract(-1, -2);

        assertEquals(1, result);
    }

    @Test
    public void multiplyTwoNumbers() {
        double result = calculationService.multiply(2, 2);

        assertEquals(4, result);
    }

    @Test
    public void multiplyTwoNegativeNumbers() {
        double result = calculationService.multiply(-3, -3);

        assertEquals(9, result);
    }

    @Test
    public void divideTwoNumbers() {
        double result = calculationService.divide(10, 2);

        assertEquals(5, result);
    }

    @Test
    public void divideTwoNegativeNumbers() {
        double result = calculationService.divide(-30, -3);

        assertEquals(10, result);
    }

    @Test(expected = ArithmeticException.class)
    public void divideByZero() {
        calculationService.divide(10, 0);
    }

    @Test
    public void divideZeroBy() {
        double result = calculationService.divide(0, 3);

        assertEquals(0, result);
    }
}
