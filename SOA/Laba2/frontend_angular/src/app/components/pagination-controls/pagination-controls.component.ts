import { Component, Input, Output, EventEmitter } from '@angular/core';
import { PageLabWork } from '../../models';

@Component({
  selector: 'app-pagination-controls',
  templateUrl: './pagination-controls.component.html',
  styleUrls: ['./pagination-controls.component.css']
})
export class PaginationControlsComponent {
  @Input() page?: PageLabWork | null;
  @Output() pageChange = new EventEmitter<number>();

  get canPrev(): boolean {
    return this.page ? this.page.number > 0 : false;
  }

  get canNext(): boolean {
    return this.page ? !this.page.last : false;
  }

  onPrev(): void {
    if (this.page && this.canPrev) {
      this.pageChange.emit(this.page.number - 1);
    }
  }

  onNext(): void {
    if (this.page && this.canNext) {
      this.pageChange.emit(this.page.number + 1);
    }
  }

  onPageClick(page: number): void {
    this.pageChange.emit(page);
  }

  getPageNumbers(): number[] {
    if (!this.page) return [];
    const total = this.page.totalPages;
    const current = this.page.number;
    const range = 2;
    const pages: number[] = [];

    let start = Math.max(0, current - range);
    let end = Math.min(total - 1, current + range);

    if (end - start < range * 2) {
      if (start === 0) {
        end = Math.min(total - 1, start + range * 2);
      } else if (end === total - 1) {
        start = Math.max(0, end - range * 2);
      }
    }

    for (let i = start; i <= end; i++) {
      pages.push(i);
    }
    return pages;
  }
}
