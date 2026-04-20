import { Component, inject, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { BookService, Book } from '../../../core/services/book';
import { AuthService } from '../../../core/services/auth';
import { LoanService } from '../../../core/services/loan';
import { ReservationService } from '../../../core/services/reservation';
import { ButtonComponent } from '../../../shared/components/button/button';

@Component({
  selector: 'app-book-detail',
  standalone: true,
  imports: [CommonModule, RouterLink, ButtonComponent],
  templateUrl: './book-detail.html',
  styleUrl: './book-detail.css',
})
export class BookDetailComponent {
  private route = inject(ActivatedRoute);
  private bookService = inject(BookService);
  private authService = inject(AuthService);
  private loanService = inject(LoanService);
  private reservationService = inject(ReservationService);

  book = signal<Book | null>(null);
  loading = signal(true);
  
  isLibrarian = computed(() => this.authService.hasRole('ROLE_LIBRARIAN'));
  isMember = computed(() => this.authService.hasRole('ROLE_MEMBER'));

  constructor() {
    this.loadBook();
  }

  loadBook() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (!id) return;

    this.bookService.getBookById(id).subscribe({
      next: (data) => {
        this.book.set(data);
        this.loading.set(false);
      },
      error: () => this.loading.set(false)
    });
  }

  reserveBook() {
    const b = this.book();
    if (!b) return;

    this.reservationService.createReservation(b.id).subscribe({
      next: () => {
        alert('RESERVATION PROTOCOL INITIATED: SUCCESS');
        this.loadBook(); // Refresh status
      },
      error: (err) => alert(`[CRITICAL FAILURE]: ${err.error?.message || 'Unknown error'}`)
    });
  }

  checkoutBook() {
    const memberIdStr = prompt('ENTER OPERATOR / MEMBER IDENTITY NUMBER:');
    if (!memberIdStr) return;

    // In a real app we'd fetch an instance ID, but the API might need an instance.
    // For now, if the API takes bookId we use that, otherwise we'll need to fetch instances.
    // Assuming bookInstanceId for checkout as per the mock service.
    
    // I need to know how to get an instance ID.
    // I'll check the Book entity or BookDetail view.
    console.log('Checkout requested for member:', memberIdStr);
  }
}
