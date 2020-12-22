import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { Calculation } from '../models/calculation';
import { Operator } from '../models/operator';

import { CalculationService } from './calculation.service';

describe('CalculationService', () => {
  let service: CalculationService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(CalculationService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should make the add call', () => {
    service.calculate(1, Operator.ADD, 1).subscribe(data => {
      expect(data).toBe(10);
    });

    const req = httpMock.expectOne(`${service.API_URL}/add/1/1`);
    expect(req.request.method).toBe('POST');
    req.flush(10);
  });

  it('should make the subtract call', () => {
    service.calculate(1, Operator.SUBTRACT, 1).subscribe(data => {
      expect(data).toBe(10);
    });

    const req = httpMock.expectOne(`${service.API_URL}/subtract/1/1`);
    expect(req.request.method).toBe('POST');
    req.flush(10);
  });

  it('should make the multiply call', () => {
    service.calculate(1, Operator.MULTIPLY, 1).subscribe(data => {
      expect(data).toBe(10);
    });

    const req = httpMock.expectOne(`${service.API_URL}/multiply/1/1`);
    expect(req.request.method).toBe('POST');
    req.flush(10);
  });

  it('should make the divide call', () => {
    service.calculate(1, Operator.DIVIDE, 1).subscribe(data => {
      expect(data).toBe(10);
    });

    const req = httpMock.expectOne(`${service.API_URL}/divide/1/1`);
    expect(req.request.method).toBe('POST');
    req.flush(10);
  });

  it('should get passt calculations', () => {
    const calculations: Calculation[] = [
      { calculation: '1 + 1 = 2' }
    ];

    service.getCalculations().subscribe(data => {
      expect(data).toBe(calculations);
    });

    const req = httpMock.expectOne(`${service.API_URL}`);
    expect(req.request.method).toBe('GET');
    req.flush(calculations);
  });

  afterEach(() => {
    httpMock.verify();
  });
});
