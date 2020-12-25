package aegon.hypotheken.assessment.resources;

import aegon.hypotheken.assessment.dto.CalculationRequest;
import aegon.hypotheken.assessment.dto.CalculationResponse;
import aegon.hypotheken.assessment.dto.OperationRequest;
import aegon.hypotheken.assessment.service.CalculationService;
import aegon.hypotheken.assessment.util.OperatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/calculations")
public class CalculationsResource {

    private final CalculationService calculationService;

    @Autowired
    public CalculationsResource(final CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @GetMapping
    public ResponseEntity<List<CalculationResponse>> getAllCalculations() {
        log.info("Receiving GET request for /calculations");

        return ResponseEntity.ok(
                calculationService.getAllCalculations()
                        .stream()
                        .map(calculation -> new CalculationResponse(
                                calculation.getCalculation()
                        ))
                        .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<Double> calculate(@RequestBody @Valid CalculationRequest calculationRequest) {
        double output = calculationRequest.getStartingNumber();
        StringBuilder calculationString = new StringBuilder("" + output);

        for (OperationRequest operation: calculationRequest.getOperations()) {
            output = calculationService.calculate(output, operation.getOperator(), operation.getValue());
            calculationString.append(String.format(" %s %s", OperatorUtil.getSymbol(operation.getOperator()), operation.getValue()));
        }

        calculationString.append(String.format(" = %s", output));
        calculationService.storeCalculation(calculationString.toString());

        return ResponseEntity.ok(output);
    }
}
