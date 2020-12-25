import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of, Subject } from 'rxjs';
import { CalculationResult } from '../models/calculation-result';
import { CalculationService } from '../services/calculation.service';

import { CalculatorOverviewComponent } from './calculator-overview.component';

describe('CalculatorOverviewComponent', () => {
  let component: CalculatorOverviewComponent;
  let fixture: ComponentFixture<CalculatorOverviewComponent>;
  let calculationService: CalculationService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ CalculatorOverviewComponent ],
      providers: [CalculationService]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CalculatorOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    calculationService = TestBed.inject(CalculationService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get calculatins on init', () => {
    const calculations: CalculationResult[] = [
      { calculation: '1 + 1 = 2' },
    ];
    spyOn(calculationService, 'getCalculations').and.returnValue(of(calculations));

    component.ngOnInit();

    expect(component.calculations).toBe(calculations);
    expect(calculationService.getCalculations).toHaveBeenCalled();
  });

  it('should subscribe to subject on init', () => {
    const calculations: CalculationResult[] = [
      { calculation: '1 + 1 = 2' }
    ];
    spyOn(calculationService, 'getCalculations').and.returnValue(of(calculations));
    let subscribed = false;
    calculationService.calculationMade$ = { subscribe(): void { subscribed = true; } } as Subject<void>;

    component.ngOnInit();

    expect(subscribed).toBeTrue();
  });

  it('should getCalculations when subject is raised', () => {
    const calculations: CalculationResult[] = [
      { calculation: '1 + 1 = 2' }
    ];
    spyOn(calculationService, 'getCalculations').and.returnValue(of(calculations));
    calculationService.calculationMade$ = new Subject<void>();

    component.ngOnInit();
    calculationService.calculationMade$.next();

    expect(calculationService.getCalculations).toHaveBeenCalledTimes(2);
  });
});
