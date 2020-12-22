import { Component, OnInit } from '@angular/core';
import { Operator } from '../models/operator';
import { CalculationService } from '../services/calculation.service';

@Component({
  selector: 'app-calculator',
  templateUrl: './calculator.component.html',
  styleUrls: ['./calculator.component.css']
})
export class CalculatorComponent implements OnInit {
  value1: string;
  value2: string;
  operator: Operator;

  constructor(private calculationService: CalculationService) {}

  ngOnInit(): void {
    this.value1 = '0';
    this.value2 = '';
  }

  get calculation(): string {
    return `${this.value1}${this.operator ? ` ${this.operator} ` : ''}${this.value2}`;
  }

  calculate(): void {
    this.calculationService.calculate(+this.value1, this.operator, +this.value2).subscribe(data => {
      this.value1 = `${data}`;
      this.operator = undefined;
      this.value2 = '';

      this.calculationService.calculationMade$.next();
    }, () => {
      this.value1 = undefined;
      this.operator = undefined;
      this.value2 = '';
    });
  }

  addNumber(value: number): void {
    if (!this.operator) {
      this.value1 = this.value1 === undefined || this.value1 === '0' ? `${value}` : (this.value1 + value).replace(/^0+/, '');
    } else {
      this.value2 = this.value2 === '' || this.value2 === '0' ? `${value}` : (this.value2 + value).replace(/^0+/, '');
    }
  }

  addOperator(operator: string): void {
    this.operator = Operator[operator];
  }
}
