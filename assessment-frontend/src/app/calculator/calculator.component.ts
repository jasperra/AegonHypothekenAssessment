import { Component, OnInit } from '@angular/core';
import { Calculation } from '../models/calculation';
import { Operation } from '../models/operation';
import { Operator } from '../models/operator';
import { CalculationService } from '../services/calculation.service';
import { convertToSymbol } from '../util/operator.util';

@Component({
  selector: 'app-calculator',
  templateUrl: './calculator.component.html',
  styleUrls: ['./calculator.component.css']
})
export class CalculatorComponent implements OnInit {
  _calculation: Calculation;

  constructor(private calculationService: CalculationService) {}

  ngOnInit(): void {
    this._calculation = {
      startingNumber: '0',
      operations: []
    };
  }

  get calculation(): string {
    const operations = this._calculation.operations.map((operation) => ` ${convertToSymbol(operation.operator)} ${operation.value}`).join('');
    return `${this._calculation.startingNumber}${operations}`
  }

  calculate(): void {
    this.calculationService.calculate(this._calculation).subscribe(data => {
      if (!data || `${data}` === 'Infinity') {
        this._calculation = {
          startingNumber: `NAN`,
          operations: []
        }
      }
      this._calculation = {
        startingNumber: `${data}`,
        operations: []
      }
      this.calculationService.calculationMade$.next();
    }, () => {
      this._calculation = {
        startingNumber: `NAN`,
        operations: []
      }
    });
  }

  addNumber(value: number): void {
    const lastEntry = this.getLastOperationEntry();

    if(!lastEntry) {
      this._calculation.startingNumber = this._calculation.startingNumber === 'NAN' || this._calculation.startingNumber === '0' ? `${value}` : (this._calculation.startingNumber + value).replace(/^0+/, '');
    } else {
      lastEntry.value = lastEntry.value === '' || lastEntry.value === '0' ? `${value}` : (lastEntry.value + value).replace(/^0+/, '');
    }
  }

  addOperator(operator: string): void {
    const lastEntry = this.getLastOperationEntry();

    if (!lastEntry || lastEntry.value !== '') {
      this._calculation.operations.push({
        operator: Operator[operator],
        value: ''
      });
    } else {
      lastEntry.operator = Operator[operator];
    }
  }

  private getLastOperationEntry(): Operation {
    return this._calculation.operations[this._calculation.operations.length - 1];
  }
}
