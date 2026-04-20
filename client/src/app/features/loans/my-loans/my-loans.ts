import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoanService, Loan } from '../../../core/services/loan';

@Component({
  selector: 'app-my-loans',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './my-loans.html',
  styleUrl: './my-loans.css',
})
export class MyLoansComponent {
  private loanService = inject(LoanService);
  
  loans = signal<Loan[]>([]);
  loading = signal(true);

  constructor() {
    this.loadLoans();
  }

  loadLoans() {
    this.loading.set(true);
    this.loanService.getMyHistory().subscribe({
      next: (data) => {
        this.loans.set(data);
        this.loading.set(false);
      },
      error: () => this.loading.set(false)
    });
  }
}
