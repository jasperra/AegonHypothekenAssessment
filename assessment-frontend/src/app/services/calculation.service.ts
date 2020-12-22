import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Calculation } from '../models/calculation';
import { Operator } from '../models/operator';

@Injectable({
  providedIn: 'root'
})
export class CalculationService {
  readonly API_URL: string = 'http://localhost:8080/calculations';
  calculationMade$: Subject<void> = new Subject();

  constructor(private httpClient: HttpClient) { }

  public getCalculations(): Observable<Calculation[]> {
    return this.httpClient.get<Calculation[]>(`${this.API_URL}`);
  }

  public calculate(value1: number, operator: Operator, value2: number): Observable<number> {
    switch (operator) {
      case Operator.ADD:
        return this.add(value1, value2);
      case Operator.SUBTRACT:
        return this.subtract(value1, value2);
      case Operator.MULTIPLY:
        return this.multiply(value1, value2);
      case Operator.DIVIDE:
        return this.divide(value1, value2);
    }
  }

  private add(value1: number, value2: number): Observable<number> {
    return this.httpClient.post<number>(`${this.API_URL}/add/${value1}/${value2}`, undefined);
  }

  private subtract(value1: number, value2: number): Observable<number> {
    return this.httpClient.post<number>(`${this.API_URL}/subtract/${value1}/${value2}`, undefined);
  }

  private multiply(value1: number, value2: number): Observable<number> {
    return this.httpClient.post<number>(`${this.API_URL}/multiply/${value1}/${value2}`, undefined);
  }

  private divide(value1: number, value2: number): Observable<number> {
    return this.httpClient.post<number>(`${this.API_URL}/divide/${value1}/${value2}`, undefined);
  }
}
