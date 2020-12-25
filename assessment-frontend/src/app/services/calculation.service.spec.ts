import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { Calculation } from '../models/calculation';
import { CalculationResult } from '../models/calculation-result';
import { Operator } from '../models/operator';

import { CalculationService } from './calculation.service';

describe('CalculationService', () => {
  let service: CalculationService;
  let httpMock: HttpTestingController;
  let calcuation: Calculation;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(CalculationService);
    httpMock = TestBed.inject(HttpTestingController);

    calcuation = {
      startingNumber: '0',
      operations: [
        {
          operator: Operator.ADD,
          value: '10'
        }
      ]
    }
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should make the add call', () => {
    service.calculate(calcuation).subscribe(data => {
      expect(data).toBe(10);
    });

    const req = httpMock.expectOne(`${service.API_URL}/calculations`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toBe(calcuation)
    req.flush(10);
  });

  it('should get past calculations', () => {
    const calculations: CalculationResult[] = [
      { calculation: '1 + 1 = 2' }
    ];

    service.getCalculations().subscribe(data => {
      expect(data).toBe(calculations);
    });

    const req = httpMock.expectOne(`${service.API_URL}/calculations`);
    expect(req.request.method).toBe('GET');
    req.flush(calculations);
  });

  afterEach(() => {
    httpMock.verify();
  });
});
