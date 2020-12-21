package aegon.hypotheken.assessment.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Calculation {
    private String calculation;
    private double answer;
}
