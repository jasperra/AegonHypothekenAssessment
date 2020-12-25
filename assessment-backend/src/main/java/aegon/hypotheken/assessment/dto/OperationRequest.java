package aegon.hypotheken.assessment.dto;

import aegon.hypotheken.assessment.model.Operator;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class OperationRequest {

    @NotNull
    private Operator operator;

    private int value;
}
