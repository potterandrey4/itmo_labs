import { Component, EventEmitter, HostBinding, Inject, Input, OnChanges, Optional, Output, SimpleChanges } from "@angular/core";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { Discipline } from "@models/discipline.model";
import { LabWorkInput } from "@models/labwork-input.model";
import { LabWork, Difficulty } from "@models/labwork.model";

const difficultyLabels: Record<Difficulty, string> = {
  VERY_EASY: 'Очень легко',
  EASY: 'Легко',
  INSANE: 'Безумно',
  TERRIBLE: 'Ужасно'
};

const difficulties: Difficulty[] = ['VERY_EASY', 'EASY', 'INSANE', 'TERRIBLE'];

interface FormErrors {
  name?: string;
  coordinates?: { x?: string; y?: string };
  minimalPoint?: string;
  personalQualitiesMaximum?: string;
  difficulty?: string;
  disciplineId?: string;
}

@Component({
  selector: 'app-labwork-form',
  templateUrl: './labwork-form.component.html',
  styleUrls: ['./labwork-form.component.css']
})
export class LabworkFormComponent implements OnChanges {
  @HostBinding('class.dialog-mode') dialogMode = false;

  @Input() selected?: LabWork | null;
  @Input() disciplines: Discipline[] = [];
  @Input() busy = false;

  @Output() create = new EventEmitter<LabWorkInput>();
  @Output() update = new EventEmitter<{ id: number; payload: LabWorkInput }>();
  @Output() delete = new EventEmitter<number>();
  @Output() reset = new EventEmitter<void>();

  form: LabWorkInput = this.createEmptyForm();
  errors: FormErrors = {};

  readonly difficultyLabels = difficultyLabels;
  readonly difficulties = difficulties;

  constructor(
    @Optional() public dialogRef?: MatDialogRef<LabworkFormComponent>,
    @Optional() @Inject(MAT_DIALOG_DATA) dialogData?: { labwork?: LabWork | null; disciplines?: Discipline[] }
  ) {
    this.dialogMode = !!this.dialogRef;

    // ← ЭТО ГЛАВНОЕ ИСПРАВЛЕНИЕ — заполняем форму при открытии диалога!
    if (this.dialogMode && dialogData) {
      this.selected = dialogData.labwork ?? null;
      if (dialogData.disciplines) {
        this.disciplines = dialogData.disciplines;
      }
      this.updateFormFromSelected();
    }
  }

  // Для inline-режима — когда selected приходит как @Input
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['selected'] || changes['disciplines']) {
      this.updateFormFromSelected();
    }
  }

  // ← Универсальная функция заполнения формы
  private updateFormFromSelected(): void {
    if (this.selected) {
      this.form = {
        name: this.selected.name,
        coordinates: { ...this.selected.coordinates },
        minimalPoint: this.selected.minimalPoint,
        personalQualitiesMaximum: this.selected.personalQualitiesMaximum || null,
        difficulty: this.selected.difficulty || null,
        disciplineId: this.selected.disciplineId || null
      };
    } else {
      this.form = this.createEmptyForm();
    }
    this.errors = {};
  }

  private createEmptyForm(): LabWorkInput {
    return {
      name: '',
      coordinates: { x: null, y: null },
      minimalPoint: null,
      personalQualitiesMaximum: null,
      difficulty: null,
      disciplineId: null
    };
  }

  validateForm(): boolean {
    const e: FormErrors = {};

    if (!this.form.name?.trim()) e.name = 'Название обязательно';
    if (this.form.coordinates.x == null) e.coordinates = { ...(e.coordinates || {}), x: 'X координата обязательна' };
    if (this.form.coordinates.y == null) e.coordinates = { ...(e.coordinates || {}), y: 'Y координата обязательна' };
    if (this.form.minimalPoint == null) e.minimalPoint = 'Минимальный балл обязателен';
    if (this.form.disciplineId == null) e.disciplineId = 'Дисциплина обязательна';

    this.errors = e;
    return Object.keys(e).length === 0;
  }

  onSubmit(): void {
    if (!this.validateForm()) return;

    const payload: LabWorkInput = { ...this.form };

    if (this.dialogMode) {
      if (this.selected) {
        this.dialogRef?.close({ id: this.selected.id!, payload });
      } else {
        this.dialogRef?.close(payload);
      }
    } else {
      if (this.selected) {
        this.update.emit({ id: this.selected.id!, payload });
      } else {
        this.create.emit(payload);
      }
    }
  }

  onDeleteClick(): void {
    if (!this.selected) return;
    if (confirm('Удалить лабораторную работу?')) {
      this.dialogMode
        ? this.dialogRef?.close({ deleteId: this.selected.id })
        : this.delete.emit(this.selected.id);
    }
  }
}