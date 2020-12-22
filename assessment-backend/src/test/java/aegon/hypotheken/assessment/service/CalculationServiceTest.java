package aegon.hypotheken.assessment.service;

import aegon.hypotheken.assessment.model.Calculation;
import aegon.hypotheken.assessment.repository.Calculationrepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        when(calculationrepository.save(any(Calculation.class))).thenReturn(null);

        double result = calculationService.add(1, 1);

        assertEquals(2, result);
        verify(calculationrepository, times(1)).save(any(Calculation.class));
    }

    @Test
    public void addTwoNegativeNumbers() {
        when(calculationrepository.save(any(Calculation.class))).thenReturn(null);

        double result = calculationService.add(-1, -1);

        assertEquals(-2, result);
        verify(calculationrepository, times(1)).save(any(Calculation.class));
    }

    @Test
    public void subtractTwoNumbers() {
        when(calculationrepository.save(any(Calculation.class))).thenReturn(null);

        double result = calculationService.subtract(1, 2);

        assertEquals(-1, result);
        verify(calculationrepository, times(1)).save(any(Calculation.class));
    }

    @Test
    public void subtractTwoNegativeNumbers() {
        when(calculationrepository.save(any(Calculation.class))).thenReturn(null);

        double result = calculationService.subtract(-1, -2);

        assertEquals(1, result);
        verify(calculationrepository, times(1)).save(any(Calculation.class));
    }

    @Test
    public void multiplyTwoNumbers() {
        when(calculationrepository.save(any(Calculation.class))).thenReturn(null);

        double result = calculationService.multiply(2, 2);

        assertEquals(4, result);
        verify(calculationrepository, times(1)).save(any(Calculation.class));
    }

    @Test
    public void multiplyTwoNegativeNumbers() {
        when(calculationrepository.save(any(Calculation.class))).thenReturn(null);

        double result = calculationService.multiply(-3, -3);

        assertEquals(9, result);
        verify(calculationrepository, times(1)).save(any(Calculation.class));
    }

    @Test
    public void divideTwoNumbers() {
        when(calculationrepository.save(any(Calculation.class))).thenReturn(null);

        double result = calculationService.divide(10, 2);

        assertEquals(5, result);
        verify(calculationrepository, times(1)).save(any(Calculation.class));
    }

    @Test
    public void divideTwoNegativeNumbers() {
        when(calculationrepository.save(any(Calculation.class))).thenReturn(null);

        double result = calculationService.divide(-30, -3);

        assertEquals(10, result);
        verify(calculationrepository, times(1)).save(any(Calculation.class));
    }

    @Test(expected = ArithmeticException.class)
    public void divideByZero() {
        calculationService.divide(10, 0);
    }

    @Test
    public void divideZeroBy() {
        when(calculationrepository.save(any(Calculation.class))).thenReturn(null);

        double result = calculationService.divide(0, 3);

        assertEquals(0, result);
        verify(calculationrepository, times(1)).save(any(Calculation.class));
    }
}
