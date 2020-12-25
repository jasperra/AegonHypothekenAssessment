import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { throwError, of, Subject } from 'rxjs';
import { CalculationService } from '../services/calculation.service';

import { CalculatorComponent } from './calculator.component';

describe('CalculatorComponent', () => {
  let component: CalculatorComponent;
  let fixture: ComponentFixture<CalculatorComponent>;
  let calculationService: CalculationService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ CalculatorComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CalculatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    calculationService = TestBed.inject(CalculationService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display 0 as default value for calculator', () => {
    expect(component.calculation).toBe('0');
  });

  it('should display 1 when adding 1 to calculation', () => {
    component.addNumber(1);
    expect(component.calculation).toBe('1');
  });

  it('should continue to add numbers', () => {
    component.addNumber(1);
    component.addNumber(0);
    expect(component.calculation).toBe('10');
  });

  it('should not display display multiple zeros', () => {
    component.addNumber(0);
    expect(component.calculation).toBe('0');
  });

  it('should display the ADD operator when added', () => {
    component.addOperator('ADD');
    expect(component.calculation).toBe('0 + ');
  });

  it('should display the SUBTRACT operator when added', () => {
    component.addOperator('SUBTRACT');
    expect(component.calculation).toBe('0 - ');
  });

  it('should display the MULTIPLY operator when added', () => {
    component.addOperator('MULTIPLY');
    expect(component.calculation).toBe('0 X ');
  });

  it('should display the DIVIDE operator when added', () => {
    component.addOperator('DIVIDE');
    expect(component.calculation).toBe('0 / ');
  });

  it('should not display two operators', () => {
    component.addOperator('ADD');
    component.addOperator('SUBTRACT');
    expect(component.calculation).toBe('0 - ');
  });

  it('should display numbers behind operator when added', () => {
    component.addOperator('ADD');
    component.addNumber(1);
    expect(component.calculation).toBe('0 + 1');
  });

  it('should display numbers no more than one leading zero', () => {
    component.addOperator('ADD');
    component.addNumber(0);
    component.addNumber(0);
    expect(component.calculation).toBe('0 + 0');
  });

  it('should display the first zero when added', () => {
    component.addOperator('ADD');
    component.addNumber(0);
    expect(component.calculation).toBe('0 + 0');
  });

  it('should continue to add numbers with operator', () => {
    component.addOperator('ADD');
    component.addNumber(1);
    component.addNumber(0);
    expect(component.calculation).toBe('0 + 10');
  });

  it('should continue to add operators after adding numbers', () => {
    component.addOperator('ADD');
    component.addNumber(1);
    component.addOperator('ADD');
    expect(component.calculation).toBe('0 + 1 + ');
  });

  it('should handle multiple operations at once', () => {
    component.addOperator('ADD');
    component.addNumber(1);
    component.addOperator('DIVIDE');
    component.addNumber(1);
    expect(component.calculation).toBe('0 + 1 / 1');
  });

  it('should send operators back with names not symbols', () => {
    component.addOperator('ADD');
    
    expect(component._calculation.operations[0].operator).toBe('ADD');
  })

  it('should be able to calculate 1 + 1', () => {
    spyOn(calculationService, 'calculate').and.returnValue(of(3));

    component.addNumber(1);
    component.addOperator('ADD');
    component.addNumber(1);
    component.calculate();

    expect(component.calculation).toBe('3');
    expect(calculationService.calculate).toHaveBeenCalled();
  });

  it('should continue to function after calculating', () => {
    spyOn(calculationService, 'calculate').and.returnValue(of(3));

    component.addNumber(1);
    component.addOperator('ADD');
    component.addNumber(1);
    component.calculate();

    expect(component.calculation).toBe('3');
    expect(calculationService.calculate).toHaveBeenCalled();

    component.addNumber(1);

    expect(component.calculation).toBe('31');
    component.addOperator('ADD');
    component.addNumber(1);

    expect(component.calculation).toBe('31 + 1');
  });

  it('should raise an event when calculation was successful', () => {
    spyOn(calculationService, 'calculate').and.returnValue(of(3));
    let eventRaised = false;
    calculationService.calculationMade$ = { next(): void { eventRaised = true; } } as Subject<void>;

    component.addNumber(1);
    component.addOperator('ADD');
    component.addNumber(1);
    component.calculate();

    expect(eventRaised).toBeTrue();
  });

  it('should display undefined when calulation fails', () => {
    spyOn(calculationService, 'calculate').and.returnValue(throwError('400'));

    component.calculate();

    expect(component.calculation).toBe('NAN');
  });

  it('should be able to add a number after failure to calculate', () => {
    component._calculation.startingNumber = 'NAN';

    component.addNumber(2);

    expect(component.calculation).toBe('2');
  });
});
