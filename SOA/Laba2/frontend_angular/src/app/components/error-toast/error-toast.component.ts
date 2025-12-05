import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-error-toast',
  templateUrl: './error-toast.component.html',
  styleUrls: ['./error-toast.component.css']
})
export class ErrorToastComponent {
  @Input() message: string | null = null;
  @Input() tone: 'error' | 'success' = 'error';
  @Output() close = new EventEmitter<void>();
}
