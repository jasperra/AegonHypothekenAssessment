package aegon.hypotheken.assessment.resources;

import aegon.hypotheken.assessment.dto.CalculationResponse;
import aegon.hypotheken.assessment.service.CalculationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

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

    @PostMapping("/add/{input1}/{input2}")
    public ResponseEntity<Double> add(@PathVariable int input1, @PathVariable int input2) {
        log.info("Calculating: adding {} to {}", input1, input2);

        return ResponseEntity.ok(calculationService.add(input1, input2));
    }

    @PostMapping("/subtract/{input1}/{input2}")
    public ResponseEntity<Double> subtract(@PathVariable int input1, @PathVariable int input2) {
        log.info("Calculating: subtracting {} from {}", input1, input2);

        return ResponseEntity.ok(calculationService.subtract(input1, input2));
    }

    @PostMapping("/multiply/{input1}/{input2}")
    public ResponseEntity<Double> multiply(@PathVariable int input1, @PathVariable int input2) {
        log.info("Calculating: multiplying {} by {}", input1, input2);

        return ResponseEntity.ok(calculationService.multiply(input1, input2));
    }

    @PostMapping("/divide/{input1}/{input2}")
    public ResponseEntity<Double> divide(@PathVariable int input1, @PathVariable int input2) {
        log.info("Calculating: dividing {} by {}", input1, input2);

        if (input2 == 0) {
            log.error("Can't divide by 0: {} - {}", input1, input2);

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't divide by 0");
        }

        return ResponseEntity.ok(calculationService.divide(input1, input2));
    }
}
