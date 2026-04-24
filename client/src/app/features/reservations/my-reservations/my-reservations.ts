import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReservationService, Reservation } from '../../../core/services/reservation';
import { ToastService } from '../../../core/services/toast';
import { ButtonComponent } from '../../../shared/components/button/button';

@Component({
  selector: 'app-my-reservations',
  standalone: true,
  imports: [CommonModule, ButtonComponent],
  templateUrl: './my-reservations.html',
})
export class MyReservationsComponent {
  private reservationService = inject(ReservationService);
  private toastService = inject(ToastService);

  reservations = signal<Reservation[]>([]);
  loading = signal(true);
  confirmCancelId = signal<number | null>(null);

  constructor() {
    this.loadReservations();
  }

  loadReservations() {
    this.loading.set(true);
    this.reservationService.getMyReservations().subscribe({
      next: (data) => {
        this.reservations.set(data);
        this.loading.set(false);
      },
      error: () => this.loading.set(false)
    });
  }

  promptCancel(id: number) {
    this.confirmCancelId.set(id);
  }

  dismissCancel() {
    this.confirmCancelId.set(null);
  }

  confirmCancel() {
    const id = this.confirmCancelId();
    if (id == null) return;
    this.confirmCancelId.set(null);
    this.reservationService.cancelReservation(id).subscribe({
      next: () => {
        this.toastService.success('Reservation cancelled.');
        this.loadReservations();
      },
      error: (err: any) => this.toastService.error(err.error?.message || 'Failed to cancel reservation.')
    });
  }
}
