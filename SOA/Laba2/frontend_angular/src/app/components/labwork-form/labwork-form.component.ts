import { Component, EventEmitter, HostBinding, Inject, Input, OnChanges, Optional, Output, SimpleChanges, ViewChild } from "@angular/core";
import { NgModel, NgForm } from '@angular/forms';
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
  @ViewChild('formRef') private _formRef?: NgForm;

  readonly difficultyLabels = difficultyLabels;
  readonly difficulties = difficulties;

  constructor(
    @Optional() public dialogRef?: MatDialogRef<LabworkFormComponent>,
    @Optional() @Inject(MAT_DIALOG_DATA) dialogData?: { labwork?: LabWork | null; disciplines?: Discipline[] }
  ) {
    this.dialogMode = !!this.dialogRef;

    if (this.dialogMode && dialogData) {
      this.selected = dialogData.labwork ?? null;
      if (dialogData.disciplines) {
        this.disciplines = dialogData.disciplines;
      }
      this.updateFormFromSelected();
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['selected'] || changes['disciplines']) {
      this.updateFormFromSelected();
    }
  }

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

    if (!this.form.name?.trim()) {
      e.name = 'Название обязательно';
    }

    const numRegex = /^\s*-?\d*\.?\d+(?:e[+-]?\d+)?\s*$/i;

    const xStr = this.form.coordinates.x?.toString().trim() || '';
    if (xStr === '') {
      e.coordinates = { ...(e.coordinates || {}), x: 'X координата обязательна' };
    } else if (!numRegex.test(xStr)) {
      e.coordinates = { ...(e.coordinates || {}), x: 'X координата должна быть числом' };
    } else {
      const xVal = parseFloat(xStr);
      if (isNaN(xVal) || !isFinite(xVal)) {
        e.coordinates = { ...(e.coordinates || {}), x: 'X координата должна быть конечным числом' };
      }
    }

    const yStr = this.form.coordinates.y?.toString().trim() || '';
    if (yStr === '') {
      e.coordinates = { ...(e.coordinates || {}), y: 'Y координата обязательна' };
    } else if (!numRegex.test(yStr)) {
      e.coordinates = { ...(e.coordinates || {}), y: 'Y координата должна быть числом' };
    } else {
      const yVal = parseFloat(yStr);
      if (isNaN(yVal) || !isFinite(yVal)) {
        e.coordinates = { ...(e.coordinates || {}), y: 'Y координата должна быть конечным числом' };
      } else if (yVal <= -156) {
        e.coordinates = { ...(e.coordinates || {}), y: 'Y координата должна быть больше -156' };
      }
    }

    const minStr = this.form.minimalPoint?.toString().trim() || '';
    if (minStr === '') {
      e.minimalPoint = 'Минимальный балл обязателен';
    } else if (!numRegex.test(minStr)) {
      e.minimalPoint = 'Минимальный балл должен быть числом';
    } else {
      const minVal = parseFloat(minStr);
      if (isNaN(minVal) || !isFinite(minVal)) {
        e.minimalPoint = 'Минимальный балл должен быть конечным числом';
      } else if (minVal <= 0) {
        e.minimalPoint = 'Минимальный балл должен быть больше 0';
      }
    }

    if (this.form.personalQualitiesMaximum != null && this.form.personalQualitiesMaximum.toString().trim() !== '') {
      const pqStr = this.form.personalQualitiesMaximum.toString().trim();
      if (!numRegex.test(pqStr)) {
        e.personalQualitiesMaximum = 'Личностные качества (макс) должны быть числом';
      } else {
        const pqVal = parseFloat(pqStr);
        if (isNaN(pqVal) || !isFinite(pqVal)) {
          e.personalQualitiesMaximum = 'Личностные качества (макс) должны быть конечным числом';
        } else if (pqVal <= 0) {
          e.personalQualitiesMaximum = 'Личностные качества (макс) должны быть больше 0';
        }
      }
    }

    if (this.form.difficulty == null) {
      e.difficulty = 'Сложность обязательна';
    }

    if (this.form.disciplineId == null) {
      e.disciplineId = 'Дисциплина обязательна';
    }

    this.errors = e;
    return Object.keys(e).length === 0;
  }

  onSubmit(formRef?: NgForm): void {
    const actualFormRef = formRef ?? this._formRef;
    if (formRef) {
      Object.keys(formRef.controls).forEach(key => {
        formRef.controls[key].markAsTouched();
      });
    }

    if (!this.validateForm()) {
      if (actualFormRef) {
        this.markAndSetAllControlErrors(actualFormRef);
      }
      return;
    }

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

  onInputX(event: Event): void {
    const input = event.target as HTMLInputElement;
    input.value = this.filterNumber(input.value);
    this.form.coordinates.x = input.value;
  }

  onBlurX(control?: NgModel): void {
    this.validateForm();
    if (control && control.control) {
      const err = this.errors.coordinates?.x;
      control.control.setErrors(err ? { custom: true } : null);
      if (err) control.control.markAsTouched();
    }
  }

  onInputY(event: Event): void {
    const input = event.target as HTMLInputElement;
    input.value = this.filterNumber(input.value);
    this.form.coordinates.y = input.value;
  }

  onBlurY(control?: NgModel): void {
    this.validateForm();
    if (control && control.control) {
      const err = this.errors.coordinates?.y;
      control.control.setErrors(err ? { custom: true } : null);
      if (err) control.control.markAsTouched();
    }
  }

  onInputMinimalPoint(event: Event): void {
    const input = event.target as HTMLInputElement;
    input.value = this.filterPositiveNumber(input.value);
    this.form.minimalPoint = input.value;
  }

  onBlurMinimalPoint(control?: NgModel): void {
    this.validateForm();
    if (control && control.control) {
      const err = this.errors.minimalPoint;
      control.control.setErrors(err ? { custom: true } : null);
      if (err) control.control.markAsTouched();
    }
  }

  onInputPersonalQualities(event: Event): void {
    const input = event.target as HTMLInputElement;
    input.value = this.filterPositiveNumber(input.value);
    this.form.personalQualitiesMaximum = input.value;
    if (this.errors.personalQualitiesMaximum) {
      this.validateForm();
    }
  }

  private markAndSetAllControlErrors(formRef: NgForm) {
    if (!formRef || !formRef.controls) return;
    const map: Record<string, string | undefined> = {
      coordX: this.errors.coordinates?.x,
      coordY: this.errors.coordinates?.y,
      minimalPoint: this.errors.minimalPoint,
      pqMax: this.errors.personalQualitiesMaximum,
      difficulty: this.errors.difficulty,
      disciplineId: this.errors.disciplineId
    };
    Object.keys(map).forEach(key => {
      const ctrl = formRef.controls[key];
      if (!ctrl) return;
      const errMsg = map[key];
      if (errMsg) {
        ctrl.setErrors({ custom: true });
        try { ctrl.markAsTouched(); } catch (e) { /* ignore */ }
      } else {
        ctrl.setErrors(null);
      }
    });
  }

  private filterNumber(value: string): string {
    let result = value.replace(/[^0-9.-]/g, '');
    const parts = result.split('.');
    if (parts.length > 2) {
      result = parts[0] + '.' + parts.slice(1).join('');
    }
    result = result.replace(/-/g, (match, offset) => offset === 0 ? match : '');
    return result;
  }

  private filterPositiveNumber(value: string): string {
    let result = value.replace(/[^0-9.]/g, '');
    const parts = result.split('.');
    if (parts.length > 2) {
      result = parts[0] + '.' + parts.slice(1).join('');
    }
    return result;
  }
}