import { Component, inject, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { BookService, Book } from '../../../core/services/book';
import { AuthService } from '../../../core/services/auth';
import { CardComponent } from '../../../shared/components/card/card';
import { ButtonComponent } from '../../../shared/components/button/button';

@Component({
  selector: 'app-book-detail',
  standalone: true,
  imports: [CommonModule, RouterLink, CardComponent, ButtonComponent],
  templateUrl: './book-detail.html',
  styleUrl: './book-detail.css',
})
export class BookDetailComponent {
  private route = inject(ActivatedRoute);
  private bookService = inject(BookService);
  private authService = inject(AuthService);

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
    // Logic for reservation will be integrated later
    console.log('Reserving book:', this.book()?.id);
  }

  checkoutBook() {
    // Logic for librarian checkout will be integrated later
    console.log('Checking out book:', this.book()?.id);
  }
}
