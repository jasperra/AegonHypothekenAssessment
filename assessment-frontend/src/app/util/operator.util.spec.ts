import { TestBed } from "@angular/core/testing";
import { Operator } from "../models/operator";
import { convertToSymbol } from "./operator.util";

describe('OperatorUtil', () => {
    it('should convert ADD to +', () => {
        expect(convertToSymbol(Operator.ADD)).toBe('+');
    });

    it('should convert SUBTRACT to -', () => {
        expect(convertToSymbol(Operator.SUBTRACT)).toBe('-');
    });

    it('should convert MULTIPLY to X', () => {
        expect(convertToSymbol(Operator.MULTIPLY)).toBe('X');
    });

    it('should convert DIVIDE to /', () => {
        expect(convertToSymbol(Operator.DIVIDE)).toBe('/');
    });
});