package aegon.hypotheken.assessment.util;

import aegon.hypotheken.assessment.model.Operator;

public class OperatorUtil {

    private OperatorUtil() {}

    public static String getSymbol(Operator operator) {
        return switch (operator) {
            case ADD -> "+";
            case SUBTRACT -> "-";
            case MULTIPLY -> "X";
            case DIVIDE -> "/";
        };
    }
}
