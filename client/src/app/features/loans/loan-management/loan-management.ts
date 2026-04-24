import { Component, inject, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoanService, Loan } from '../../../core/services/loan';
import { ToastService } from '../../../core/services/toast';
import { ButtonComponent } from '../../../shared/components/button/button';

type LoanFilter = 'ALL' | 'ACTIVE' | 'OVERDUE' | 'RETURNED';

@Component({
  selector: 'app-loan-management',
  standalone: true,
  imports: [CommonModule, ButtonComponent],
  templateUrl: './loan-management.html',
  styleUrl: './loan-management.css',
})
export class LoanManagementComponent {
  private loanService = inject(LoanService);
  private toastService = inject(ToastService);

  allLoans = signal<Loan[]>([]);
  loading = signal(true);
  activeFilter = signal<LoanFilter>('ALL');
  confirmReturnId = signal<number | null>(null);

  readonly filters: LoanFilter[] = ['ALL', 'ACTIVE', 'OVERDUE', 'RETURNED'];

  filteredLoans = computed(() => {
    const f = this.activeFilter();
    if (f === 'ALL') return this.allLoans();
    return this.allLoans().filter(l => l.status === f);
  });

  constructor() {
    this.loadLoans();
  }

  loadLoans() {
    this.loading.set(true);
    this.loanService.getAllLoans().subscribe({
      next: (data) => {
        this.allLoans.set(data);
        this.loading.set(false);
      },
      error: () => this.loading.set(false)
    });
  }

  setFilter(f: LoanFilter) {
    this.activeFilter.set(f);
  }

  promptReturn(loanId: number) {
    this.confirmReturnId.set(loanId);
  }

  cancelReturn() {
    this.confirmReturnId.set(null);
  }

  confirmReturn() {
    const id = this.confirmReturnId();
    if (id == null) return;
    this.confirmReturnId.set(null);
    this.loanService.returnBook(id).subscribe({
      next: () => {
        this.toastService.success('Book returned successfully.');
        this.loadLoans();
      },
      error: (err: any) => this.toastService.error(`Failed to return book: ${err.error?.message || 'Unknown error'}`)
    });
  }
}
