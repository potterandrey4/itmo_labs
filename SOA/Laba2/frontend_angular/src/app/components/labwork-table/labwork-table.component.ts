import { Component, Input, Output, EventEmitter, OnChanges, SimpleChanges } from '@angular/core';
import { LabWork, PageLabWork, Discipline, Difficulty } from '../../models';
import { difficultyLabels } from '../../models';
import { MatTableDataSource } from '@angular/material/table';

interface SortItem {
  column: string;
  direction: 'asc' | 'desc';
}

@Component({
  selector: 'app-labwork-table',
  templateUrl: './labwork-table.component.html',
  styleUrls: ['./labwork-table.component.css']
})
export class LabworkTableComponent {
  @Input() page?: PageLabWork | null;
  @Input() disciplines: Discipline[] = [];
  @Input() selectedId?: number | null;
  @Input() selected?: LabWork | null;
  @Input() loading = false;
  @Input() groupByDiscipline = false;
  @Input() disciplineGroups?: Record<string, number> | null;
  @Output() select = new EventEmitter<LabWork>();
  @Output() edit = new EventEmitter<LabWork>();
  @Output() delete = new EventEmitter<number>();
  @Output() sortChange = new EventEmitter<string | null>();

  difficultyLabels: Record<Difficulty, string> = difficultyLabels;
  displayedColumns: string[] = ['id', 'name', 'difficulty', 'minimalPoint', 'personalQualitiesMaximum', 'coordinates', 'discipline', 'creationDate', 'actions'];
  dataSource = new MatTableDataSource<LabWork>([]);

  sorts: SortItem[] = [];

  getSortDirection(column: string): 'asc' | 'desc' | null {
    const item = this.sorts.find(s => s.column === column);
    return item ? item.direction : null;
  }

  getSortIndex(column: string): number {
    return this.sorts.findIndex(s => s.column === column);
  }

  toggleSort(column: string): void {
    const existingIndex = this.sorts.findIndex(s => s.column === column);

    if (existingIndex === -1) {
      this.sorts.push({ column, direction: 'desc' });
    } else {
      const existing = this.sorts[existingIndex];
      if (existing.direction === 'desc') {
        this.sorts[existingIndex] = { column, direction: 'asc' };
      } else {
        this.sorts.splice(existingIndex, 1);
      }
    }

    this.emitSortChange();
  }

  private emitSortChange(): void {
    if (this.sorts.length === 0) {
      this.sortChange.emit(null);
    } else {
      const sortString = this.sorts.map(s => `${s.column},${s.direction}`).join(';');
      this.sortChange.emit(sortString);
    }
  }

  onRowClick(lab: LabWork): void {
    this.select.emit(lab);
  }

  onEditClick(lab: LabWork, event: Event): void {
    event.stopPropagation();
    this.edit.emit(lab);
  }

  onDeleteClick(labworkId: number, event: Event): void {
    event.stopPropagation();
    if (confirm('Удалить лабораторную работу?')) {
      this.delete.emit(labworkId);
    }
  }

  getDisciplineText(lab: LabWork): string {
    const discipline = this.disciplines.find(d => d.id === lab.disciplineId);
    return discipline ? `${discipline.name}${discipline.practiceHours ? ` (${discipline.practiceHours} ч.)` : ''}` : '—';
  }

  getDifficultyLabel(lab: LabWork): string {
    return lab.difficulty ? this.difficultyLabels[lab.difficulty] : '—';
  }

  formatDate(date: string): string {
    const dateObj = new Date(date);
    return isNaN(dateObj.getTime()) ? '—' : dateObj.toLocaleString('ru-RU');
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['page']) {
      this.dataSource.data = this.page?.content ?? [];
    }
  }
}
