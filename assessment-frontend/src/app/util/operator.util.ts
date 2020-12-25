import { Operator } from "../models/operator";

export function convertToSymbol(operator: Operator): string {
    switch(operator) {
        case Operator.ADD:
            return '+';
        case Operator.SUBTRACT:
            return '-';
        case Operator.MULTIPLY:
            return 'X';
        case Operator.DIVIDE:
            return '/';
    }
}