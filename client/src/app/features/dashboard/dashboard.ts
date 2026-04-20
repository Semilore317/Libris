import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReportService, DashboardSummary } from '../../core/services/report';
import { CardComponent } from '../../shared/components/card/card';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class DashboardComponent {
  private reportService = inject(ReportService);
  
  summary = signal<DashboardSummary | null>(null);

  constructor() {
    this.loadSummary();
  }

  loadSummary() {
    this.reportService.getDashboardSummary().subscribe({
      next: (data) => this.summary.set(data),
      error: (err) => console.error('Archive retrieval failure:', err)
    });
  }
}
