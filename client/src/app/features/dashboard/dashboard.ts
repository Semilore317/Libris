import { Component, inject, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ReportService, DashboardSummary } from '../../core/services/report';
import { AuthService } from '../../core/services/auth';
import { LoanService, Loan } from '../../core/services/loan';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class DashboardComponent {
  private reportService = inject(ReportService);
  private authService = inject(AuthService);
  private loanService = inject(LoanService);
  
  summary = signal<DashboardSummary | null>(null);
  myLoans = signal<Loan[]>([]);
  isLibrarian = computed(() => this.authService.hasRole('ROLE_LIBRARIAN'));

  constructor() {
    if (this.isLibrarian()) {
      this.loadSummary();
    } else {
      this.loadMyStats();
    }
  }

  loadSummary() {
    this.reportService.getDashboardSummary().subscribe({
      next: (data: DashboardSummary) => this.summary.set(data),
      error: (err: any) => console.error('Archive retrieval failure:', err)
    });
  }

  loadMyStats() {
    this.loanService.getMyHistory().subscribe({
      next: (loans: Loan[]) => this.myLoans.set(loans),
      error: (err: any) => console.error('Loan history retrieval failure:', err)
    });
  }
}
