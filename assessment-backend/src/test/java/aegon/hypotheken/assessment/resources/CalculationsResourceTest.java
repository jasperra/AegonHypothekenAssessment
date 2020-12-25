package aegon.hypotheken.assessment.resources;

import aegon.hypotheken.assessment.dto.CalculationRequest;
import aegon.hypotheken.assessment.dto.CalculationResponse;
import aegon.hypotheken.assessment.dto.OperationRequest;
import aegon.hypotheken.assessment.model.Calculation;
import aegon.hypotheken.assessment.model.Operator;
import aegon.hypotheken.assessment.service.CalculationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
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
    public void calculate() {
        when(calculationService.calculate(0, Operator.ADD, 10)).thenReturn(10D);
        doNothing().when(calculationService).storeCalculation("0.0 + 10 = 10.0");

        CalculationRequest calculationRequest = CalculationRequest.builder()
                .startingNumber(0)
                .operations(Collections.singletonList(OperationRequest.builder()
                        .operator(Operator.ADD)
                        .value(10)
                        .build()))
                .build();

        ResponseEntity<Double> result = calculationsResource.calculate(calculationRequest);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(10, result.getBody(), 0);
        verify(calculationService, times(1)).calculate(0, Operator.ADD, 10);
        verify(calculationService, times(1)).storeCalculation("0.0 + 10 = 10.0");
    }

    @Test
    public void calculateMultipleAtOnce() {
        when(calculationService.calculate(anyDouble(), any(), anyDouble())).thenReturn(10D);
        doNothing().when(calculationService).storeCalculation("0.0 + 10 = 10.0");

        List<OperationRequest> operations = Arrays.asList(
                OperationRequest.builder()
                        .operator(Operator.ADD)
                        .value(10)
                        .build(),
                OperationRequest.builder()
                        .operator(Operator.ADD)
                        .value(10)
                        .build());
        CalculationRequest calculationRequest = CalculationRequest.builder()
                .startingNumber(0)
                .operations(operations)
                .build();

        ResponseEntity<Double> result = calculationsResource.calculate(calculationRequest);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(10, result.getBody(), 0);
        verify(calculationService, times(1)).calculate(0, Operator.ADD, 10);
        verify(calculationService, times(1)).calculate(10, Operator.ADD, 10);
        verify(calculationService, times(1)).storeCalculation("0.0 + 10 + 10 = 10.0");
    }
}
