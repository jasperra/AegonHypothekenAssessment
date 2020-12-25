package aegon.hypotheken.assessment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Builder
public class CalculationRequest {

    private int startingNumber;

    @Valid
    @NotNull
    @Size(min = 1)
    private List<OperationRequest> operations;
}
