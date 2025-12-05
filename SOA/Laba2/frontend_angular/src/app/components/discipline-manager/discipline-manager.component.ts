import { Component, Input, Output, EventEmitter, HostBinding, Inject, Optional } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Discipline, DisciplineInput } from '../../models';
import { LabworkService } from '../../services/labwork.service';

@Component({
  selector: 'app-discipline-manager',
  templateUrl: './discipline-manager.component.html',
  styleUrls: ['./discipline-manager.component.css']
})
export class DisciplineManagerComponent {
  @HostBinding('class.dialog-mode') dialogMode = false;
  @Input() disciplines: Discipline[] = [];
  @Input() busy = false;
  @Output() create = new EventEmitter<DisciplineInput>();
  @Output() delete = new EventEmitter<number>();
  @Output() disciplineChange = new EventEmitter<void>();

  newDiscipline: DisciplineInput = { name: '', practiceHours: null };
  editingDiscipline: Discipline | null = null;

  constructor(
    private labworkService: LabworkService,
    private snackBar: MatSnackBar,
    @Optional() @Inject(MAT_DIALOG_DATA) private data?: { disciplines?: Discipline[] },
    @Optional() public dialogRef?: MatDialogRef<DisciplineManagerComponent>
  ) {
    this.dialogMode = !!this.dialogRef;
    if (this.dialogMode && data?.disciplines) {
      this.disciplines = [...data.disciplines];
    }
  }

  createDiscipline(): void {
    if (!this.newDiscipline.name || !this.newDiscipline.name.trim()) return;
    const payload = {
      name: this.newDiscipline.name.trim(),
      practiceHours: this.newDiscipline.practiceHours
    };
    if (this.dialogMode) {
      this.labworkService.createDiscipline(payload).subscribe({
        next: (created) => {
          this.disciplines.push(created);
          this.newDiscipline = { name: '', practiceHours: null };
          this.snackBar.open('Дисциплина создана', 'OK', { duration: 3000, panelClass: ['mat-snack-success'] });
        },
        error: (err) => {
          this.snackBar.open((err && err.payload?.message) || 'Ошибка при создании', 'OK', { duration: 3500, panelClass: ['mat-snack-error'] });
        }
      });
    } else {
      this.create.emit(payload);
      this.newDiscipline = { name: '', practiceHours: null };
    }
  }

  deleteDiscipline(id: number): void {
    if (this.dialogMode) {
      if (!confirm('Удалить дисциплину?')) return;
      this.labworkService.deleteDiscipline(id).subscribe({
        next: () => {
          this.disciplines = this.disciplines.filter(d => d.id !== id);
          this.snackBar.open('Дисциплина удалена', 'OK', { duration: 3000, panelClass: ['mat-snack-success'] });
        },
        error: (err) => this.snackBar.open((err && err.payload?.message) || 'Ошибка при удалении', 'OK', { duration: 3500, panelClass: ['mat-snack-error'] })
      });
    } else {
      this.delete.emit(id);
    }
  }

  startEditing(discipline: Discipline): void {
    this.editingDiscipline = { ...discipline };
  }

  cancelEditing(): void {
    this.editingDiscipline = null;
  }

  updateDiscipline(): void {
    if (!this.editingDiscipline || !this.editingDiscipline.name.trim()) return;
    const payload = {
      name: this.editingDiscipline.name.trim(),
      practiceHours: this.editingDiscipline.practiceHours
    };
    if (this.dialogMode) {
      this.labworkService.updateDiscipline(this.editingDiscipline.id, payload).subscribe({
        next: (updated) => {
          const index = this.disciplines.findIndex(d => d.id === updated.id);
          if (index !== -1) this.disciplines[index] = updated;
          this.editingDiscipline = null;
          this.snackBar.open('Дисциплина обновлена', 'OK', { duration: 3000, panelClass: ['mat-snack-success'] });
        },
        error: (err) => {
          this.snackBar.open((err && err.payload?.message) || 'Ошибка при обновлении', 'OK', { duration: 3500, panelClass: ['mat-snack-error'] });
        }
      });
    } else {
      // For inline mode, emit update event
      this.disciplineChange.emit();
      this.editingDiscipline = null;
    }
  }
}
