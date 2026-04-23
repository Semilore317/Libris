import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoanService, Loan } from '../../../core/services/loan';
import { ToastService } from '../../../core/services/toast';
import { ButtonComponent } from '../../../shared/components/button/button';

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
  
  loans = signal<Loan[]>([]);
  loading = signal(true);

  constructor() {
    this.loadLoans();
  }

  loadLoans() {
    this.loading.set(true);
    this.loanService.getAllLoans().subscribe({
      next: (data) => {
        this.loans.set(data);
        this.loading.set(false);
      },
      error: () => this.loading.set(false)
    });
  }

  onReturn(loanId: number) {
    if (confirm('Confirm book return?')) {
      this.loanService.returnBook(loanId).subscribe({
        next: () => {
          this.toastService.success('Book returned successfully.');
          this.loadLoans();
        },
        error: (err: any) => this.toastService.error(`Failed to return book: ${err.error?.message || 'Unknown error'}`)
      });
    }
  }
}
