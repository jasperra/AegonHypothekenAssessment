import { Component, OnInit } from '@angular/core';
import { Calculation } from '../models/calculation';
import { CalculationService } from '../services/calculation.service';

@Component({
  selector: 'app-calculator-overview',
  templateUrl: './calculator-overview.component.html',
  styleUrls: ['./calculator-overview.component.css']
})
export class CalculatorOverviewComponent implements OnInit {
  calculations: Calculation[];

  constructor(private calculationService: CalculationService) { }

  ngOnInit(): void {
    this.getCalculations();

    this.calculationService.calculationMade$.subscribe(() => {
      this.getCalculations();
    });
  }

  private getCalculations(): void {
    this.calculationService.getCalculations().subscribe(data => {
      this.calculations = data;
    });
  }

}
