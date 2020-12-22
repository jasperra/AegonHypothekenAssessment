package aegon.hypotheken.assessment.resources;

import aegon.hypotheken.assessment.dto.CalculationResponse;
import aegon.hypotheken.assessment.model.Calculation;
import aegon.hypotheken.assessment.service.CalculationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CalculationsResource.class})
public class CalculationsResourceTest {

    @MockBean
    private CalculationService calculationService;

    @Autowired
    private CalculationsResource calculationsResource;

    @Test
    public void getAll() {
        Calculation calculation = Calculation.builder().calculation("1 + 1 = 2").build();
        when(calculationService.getAllCalculations()).thenReturn(Collections.singletonList(calculation));

        ResponseEntity<List<CalculationResponse>> result = calculationsResource.getAllCalculations();

        verify(calculationService, times(1)).getAllCalculations();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());

        CalculationResponse calculationResponse = result.getBody().get(0);
        assertEquals("1 + 1 = 2", calculationResponse.getCalculation());
    }

    @Test
    public void addTwoNumbers() {
        when(calculationService.add(2, 2)).thenReturn(4D);

        ResponseEntity<Double> result = calculationsResource.add(2, 2);

        verify(calculationService, times(1)).add(2, 2);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(4, result.getBody(), 0);
    }

    @Test
    public void subtractTwoNumbers() {
        when(calculationService.subtract(2, 2)).thenReturn(0D);

        ResponseEntity<Double> result = calculationsResource.subtract(2, 2);

        verify(calculationService, times(1)).subtract(2, 2);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(0, result.getBody(), 0);
    }

    @Test
    public void multiplyTwoNumbers() {
        when(calculationService.multiply(2, 3)).thenReturn(6D);

        ResponseEntity<Double> result = calculationsResource.multiply(2, 3);

        verify(calculationService, times(1)).multiply(2, 3);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(6, result.getBody(), 0);
    }

    @Test
    public void divideTwoNumbers() {
        when(calculationService.divide(9, 3)).thenReturn(3D);

        ResponseEntity<Double> result = calculationsResource.divide(9, 3);

        verify(calculationService, times(1)).divide(9, 3);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(3, result.getBody(), 0);
    }

    @Test
    public void divideByZero() {
        when(calculationService.divide(9, 0)).thenThrow(ArithmeticException.class);

        try {
            calculationsResource.divide(9, 0);
        } catch (ResponseStatusException e) {
            verify(calculationService, times(0)).divide(9, 0);
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
            assertEquals("400 BAD_REQUEST \"Can't divide by 0\"", e.getMessage());
        }
    }
}
