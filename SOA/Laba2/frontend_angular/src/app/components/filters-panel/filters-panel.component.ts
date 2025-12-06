import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { LabworkSearchFilters, Difficulty, Discipline } from '../../models/models';
import { difficultyLabels } from '../../models/models';
import { LabworkService } from '../../services/labwork.service';

const difficulties: Difficulty[] = ['VERY_EASY', 'EASY', 'INSANE', 'TERRIBLE'];

@Component({
  selector: 'app-filters-panel',
  templateUrl: './filters-panel.component.html',
  styleUrls: ['./filters-panel.component.css']
})
export class FiltersPanelComponent implements OnInit {

  @Input() filters: LabworkSearchFilters = {};
  @Output() filtersChange = new EventEmitter<Partial<LabworkSearchFilters>>();
  @Output() onSearch = new EventEmitter<void>();
  @Output() onReset = new EventEmitter<void>();
  @Input() loading = false;

  disciplines: Discipline[] = [];
  difficultyLabels = difficultyLabels;
  difficulties = difficulties;

  constructor(private labworkService: LabworkService) { }

  ngOnInit(): void {
    this.labworkService.getAllDisciplines().subscribe(disciplines => {
      this.disciplines = disciplines;
    });
  }

  handleNumberChange(field: keyof LabworkSearchFilters, event: Event): void {
    const target = event.target as HTMLInputElement;
    const value = target.value;
    const num = Number(value);
    if (isNaN(num)) return;
    if (field === 'size' && (num < 1 || num > 100)) return;
    if (field === 'page' && num < 0) return;
    if (['minimalPointGreaterThan', 'personalQualitiesMaximumGreaterThan', 'xGreaterThan', 'xLessThan', 'yGreaterThan', 'yLessThan'].includes(field as string) && (num < -1e10 || num > 1e10)) return;
    this.filtersChange.emit({ [field]: value === '' ? undefined : num });
  }

  handleDifficultyChange(event: any): void {
    const value = event && (event.value ?? event.target?.value);
    this.filtersChange.emit({ minDifficulty: (value || undefined) as Difficulty | undefined });
  }

  handleDisciplineChange(event: any): void {
    const value = event && (event.value ?? event.target?.value);
    this.filtersChange.emit({ disciplineName: value || undefined });
  }

  handleSizeChange(event: any): void {
    const value = event && (event.value ?? event.target?.value);
    this.filtersChange.emit({ size: +value });
  }

  handleTextChange(field: keyof LabworkSearchFilters, event: Event): void {
    const target = event.target as HTMLInputElement;
    const value = target.value;
    this.filtersChange.emit({ [field]: value === '' ? undefined : value });
  }

  handleDateChange(field: keyof LabworkSearchFilters, event: Event): void {
    const target = event.target as HTMLInputElement;
    const value = target.value;
    if (value === '') {
      this.filtersChange.emit({ [field]: undefined });
      return;
    }
    const withSeconds = value.length === 16 ? `${value}:00` : value;
    const dt = new Date(withSeconds);
    const iso = dt.toISOString();
    this.filtersChange.emit({ [field]: iso });
  }

  formatForInput(iso?: string): string {
    if (!iso) return '';
    try {
      const dt = new Date(iso);
      const pad = (n: number) => String(n).padStart(2, '0');
      const year = dt.getFullYear();
      const month = pad(dt.getMonth() + 1);
      const day = pad(dt.getDate());
      const hours = pad(dt.getHours());
      const minutes = pad(dt.getMinutes());
      return `${year}-${month}-${day}T${hours}:${minutes}`;
    } catch (e) {
      return '';
    }
  }
}
