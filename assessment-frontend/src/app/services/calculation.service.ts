import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Calculation } from '../models/calculation';
import { CalculationResult } from '../models/calculation-result';

@Injectable({
  providedIn: 'root'
})
export class CalculationService {
  readonly API_URL: string = 'http://localhost:8080';
  calculationMade$: Subject<void> = new Subject();

  constructor(private httpClient: HttpClient) { }

  public getCalculations(): Observable<CalculationResult[]> {
    return this.httpClient.get<CalculationResult[]>(`${this.API_URL}/calculations`);
  }

  public calculate(body: Calculation): Observable<number> {
    return this.httpClient.post<number>(`${this.API_URL}/calculations`, body);
  }
}
