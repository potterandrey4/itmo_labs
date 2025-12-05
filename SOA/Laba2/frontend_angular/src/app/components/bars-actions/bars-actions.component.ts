import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Discipline } from '../../models';

@Component({
  selector: 'app-bars-actions',
  templateUrl: './bars-actions.component.html',
  styleUrls: ['./bars-actions.component.css']
})
export class BarsActionsComponent {
  @Output() decrease = new EventEmitter<number>();
  @Output() assign = new EventEmitter<{ labworkId: number; discipline: Discipline }>();
  @Input() disciplines: Discipline[] = [];
  @Input() busy = false;

  selectedLabworkId: number | null = null;
  selectedDisciplineId: number | null = null;

  onDecrease(): void {
    if (this.selectedLabworkId) {
      this.decrease.emit(this.selectedLabworkId);
    }
  }

  onAssign(): void {
    if (this.selectedLabworkId && this.selectedDisciplineId) {
      const discipline = this.disciplines.find(d => d.id === this.selectedDisciplineId);
      if (discipline) {
        this.assign.emit({ labworkId: this.selectedLabworkId, discipline });
      }
    }
  }

  onLabworkIdChange(event: Event): void {
    const target = event.target as HTMLInputElement;
    this.selectedLabworkId = +target.value;
  }

  onDisciplineChange(event: any): void {
    const value = event && (event.value ?? event.target?.value);
    this.selectedDisciplineId = +value;
  }
}
