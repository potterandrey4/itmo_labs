import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import {
  LabWork, LabWorkInput, LabworkSearchFilters, PageLabWork,
  Discipline, DisciplineInput
} from './models';
import { DisciplineManagerComponent } from './components/discipline-manager/discipline-manager.component';
import { LabworkService } from './services/labwork.service';
import { BarsService } from './services/bars.service';
import { ApiError } from './services/http-client.service';
import { LabworkTableComponent } from './components/labwork-table/labwork-table.component';

// Если у тебя нет отдельного диалога — создадим его позже
import { LabworkFormComponent } from './components/labwork-form/labwork-form.component';

const DEFAULT_FILTERS: LabworkSearchFilters = {
  page: 0,
  size: 10
};

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  @ViewChild(LabworkTableComponent) labworkTable?: LabworkTableComponent;

  filters: LabworkSearchFilters = { ...DEFAULT_FILTERS };
  page: PageLabWork | null = null;
  selected: LabWork | null = null;
  disciplines: Discipline[] = [];

  loading = false;
  busy = false;
  quickNameFilter = '';
  groupByDiscipline = false;
  disciplineGroups: Record<string, number> | null = null;
  barsLabworkId: number | null = null;
  barsDisciplineId: number | null = null;
  sidebarCollapsed = false;

  constructor(
    private labworkService: LabworkService,
    private barsService: BarsService,
    private snackBar: MatSnackBar,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.runSearch();
    this.loadDisciplines();
  }

  onSelect(labwork: LabWork): void {
    this.selected = labwork;
  }

  runSearch(override?: Partial<LabworkSearchFilters>): void {
    const filters = { ...this.filters, ...override, page: override?.page ?? 0 };
    this.filters = filters;
    this.loading = true;

    this.labworkService.searchLabworks(filters).subscribe({
      next: (page) => {
        this.page = page;
        if (this.selected) {
          this.selected = page.content.find(l => l.id === this.selected?.id) ?? null;
        }
      },
      error: err => this.showError(err),
      complete: () => this.loading = false
    });
  }

  onQuickSearch(value: string): void {
    this.quickNameFilter = value;
    if (value.trim()) {
      this.labworkService.getByPrefixedNames(value.trim()).subscribe({
        next: items => this.page = this.mockPage(items),
        error: err => this.showError(err)
      });
    } else {
      this.runSearch();
    }
  }

  onToggleGrouping(enabled: boolean): void {
    this.groupByDiscipline = enabled;
    if (enabled) {
      this.labworkService.getGroupedByDiscipline().subscribe({
        next: groups => this.disciplineGroups = groups,
        error: err => this.showError(err)
      });
    } else {
      this.disciplineGroups = null;
      this.runSearch();
    }
  }

  onFiltersChange(changes: Partial<LabworkSearchFilters>): void {
    this.filters = { ...this.filters, ...changes };
  }

  onResetFilters(): void {
    this.filters = { ...DEFAULT_FILTERS };
    this.quickNameFilter = '';
    if (this.labworkTable) {
      this.labworkTable.sorts = [];
    }
    this.runSearch();
  }

  onSortChange(sort: string | null): void {
    this.filters.sort = sort ?? undefined;
    this.runSearch({ page: 0 });
  }

  onPageChange(page: number): void {
    this.runSearch({ page });
  }

  openCreateDialog(): void {
    const dialogRef = this.dialog.open(LabworkFormComponent, {
      width: '720px',
      maxWidth: '90vw',
      autoFocus: false,
      data: { labwork: null, disciplines: this.disciplines }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (!result) return;
      if ('deleteId' in result) {
        this.deleteLabwork(result.deleteId);
      } else {
        this.createLabwork(result);
      }
    });
  }

  openEditDialog(labwork: LabWork): void {
    const dialogRef = this.dialog.open(LabworkFormComponent, {
      width: '720px',
      maxWidth: '90vw',
      autoFocus: false,
      data: { labwork, disciplines: this.disciplines }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (!result) return;
      if ('deleteId' in result) {
        this.deleteLabwork(result.deleteId);
      } else {
        this.updateLabwork(result);
      }
    });
  }

  openDisciplineManagerDialog(): void {
    const dialogRef = this.dialog.open(DisciplineManagerComponent, {
      width: '640px',
      maxWidth: '90vw',
      autoFocus: false,
      data: { disciplines: this.disciplines }
    });

    dialogRef.afterClosed().subscribe(() => {
      this.loadDisciplines();
    });
  }

  createLabwork(input: LabWorkInput): void {
    this.withBusy(
      this.labworkService.createLabwork(input),
      () => {
        this.selected = null;
        this.runSearch({ page: 0 });
        this.showSuccess('Лабораторная создана');
      },
      err => this.showError(err)
    );
  }

  updateLabwork({ id, payload }: { id: number; payload: LabWorkInput }): void {
    this.withBusy(
      this.labworkService.updateLabwork(id, payload),
      updated => {
        this.selected = updated;
        this.runSearch();
        this.showSuccess('Лабораторная обновлена');
      },
      err => this.showError(err)
    );
  }

  deleteLabwork(id: number): void {
    if (!confirm('Удалить лабораторную работу?')) return;

    this.withBusy(
      this.labworkService.deleteLabwork(id),
      () => {
        this.selected = null;
        this.runSearch();
        this.showSuccess('Лабораторная удалена');
      },
      err => this.showError(err)
    );
  }

  decreaseDifficulty(labworkId: number): void {
    this.withBusy(
      this.barsService.decreaseDifficulty(labworkId),
      updated => {
        this.selected = updated;
        this.runSearch();
        this.showSuccess('Сложность уменьшена');
      },
      err => this.showError(err)
    );
  }

  assignDiscipline({ labworkId, discipline }: { labworkId: number; discipline: Discipline | null }): void {
    if (!discipline) return;
    this.withBusy(
      this.barsService.assignDiscipline(labworkId, discipline),
      updated => {
        this.selected = updated;
        this.runSearch();
        this.showSuccess('Дисциплина присвоена');
      },
      err => this.showError(err)
    );
  }

  createDiscipline(input: DisciplineInput): void {
    this.withBusy(
      this.labworkService.createDiscipline(input),
      created => {
        this.disciplines = [...this.disciplines, created];
        this.showSuccess('Дисциплина создана');
      },
      err => this.showError(err)
    );
  }

  deleteDiscipline(id: number): void {
    this.withBusy(
      this.labworkService.deleteDiscipline(id),
      () => {
        this.disciplines = this.disciplines.filter(d => d.id !== id);
        this.showSuccess('Дисциплина удалена');
      },
      err => this.showError(err)
    );
  }

  loadDisciplines(): void {
    this.labworkService.getAllDisciplines().subscribe({
      next: data => this.disciplines = data,
      error: err => this.showError(err)
    });
  }

  private toLabWorkInput(labwork: LabWork): LabWorkInput {
    return {
      name: labwork.name,
      coordinates: labwork.coordinates,
      minimalPoint: labwork.minimalPoint,
      personalQualitiesMaximum: labwork.personalQualitiesMaximum,
      difficulty: labwork.difficulty,
      disciplineId: labwork.disciplineId
    };
  }

  private withBusy(observable: Observable<any>, next: (value: any) => void, error?: (err: any) => void): void {
    this.busy = true;
    observable.subscribe({
      next: (value: any) => {
        next(value);
        this.busy = false;
      },
      error: (err: any) => {
        if (error) error(err);
        this.busy = false;
      }
    });
  }

  private showSuccess(message: string): void {
    this.snackBar.open(message, 'OK', { duration: 3500, panelClass: 'mat-snack-success' });
  }

  private showError(error: unknown): void {
    const msg = error instanceof ApiError ? error.payload?.message ?? error.message
      : error instanceof Error ? error.message
        : 'Неизвестная ошибка';

    this.snackBar.open(msg, 'OK', { duration: 6000, panelClass: 'mat-snack-error' });
  }

  getDisciplineById(id: number | null): Discipline | null {
    return id ? this.disciplines.find(d => d.id === id) || null : null;
  }

  private mockPage(content: LabWork[]): PageLabWork {
    const size = content.length;
    return {
      content,
      totalElements: size,
      totalPages: 1,
      size,
      number: 0,
      numberOfElements: size,
      first: true,
      last: true,
      empty: size === 0
    };
  }
}