import { Component, inject, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { BookService, Book } from '../../../core/services/book';
import { AuthService } from '../../../core/services/auth';
import { ToastService } from '../../../core/services/toast';
import { ButtonComponent } from '../../../shared/components/button/button';

export const BOOK_GENRES = [
  'FICTION', 'NON_FICTION', 'SCIENCE_FICTION', 'FANTASY', 'MYSTERY',
  'THRILLER', 'ROMANCE', 'HORROR', 'ADVENTURE', 'BIOGRAPHY', 'HISTORY',
  'POETRY', 'DRAMA', 'COMEDY', 'CLASSIC', 'SELF_HELP', 'PHILOSOPHY',
  'RELIGION', 'SCIENCE', 'TECHNOLOGY', 'OTHER'
];

@Component({
  selector: 'app-book-catalog',
  standalone: true,
  imports: [CommonModule, RouterLink, ButtonComponent, ReactiveFormsModule],
  templateUrl: './book-catalog.html',
  styleUrl: './book-catalog.css',
})
export class BookCatalogComponent {
  private bookService = inject(BookService);
  private authService = inject(AuthService);
  private toastService = inject(ToastService);
  private fb = inject(FormBuilder);

  readonly genres = BOOK_GENRES;

  books = signal<Book[]>([]);
  loading = signal(false);
  showAddForm = signal(false);
  isLibrarian = computed(() => this.authService.hasRole('ROLE_LIBRARIAN'));

  addBookForm = this.fb.group({
    title: ['', [Validators.required]],
    author: ['', [Validators.required]],
    ISBN: ['', [Validators.required]],
    genre: ['', [Validators.required]],
    publicationYear: [null as number | null],
  });

  constructor() {
    this.loadBooks();
  }

  loadBooks() {
    this.loading.set(true);
    this.bookService.getAllBooks().subscribe({
      next: (data) => {
        this.books.set(data);
        this.loading.set(false);
      },
      error: () => this.loading.set(false)
    });
  }

  onSearch(event: Event) {
    const query = (event.target as HTMLInputElement).value.trim();
    if (!query) {
      this.loadBooks();
      return;
    }
    this.loading.set(true);
    this.bookService.searchBooks(query).subscribe({
      next: (data) => {
        this.books.set(data);
        this.loading.set(false);
      },
      error: () => this.loading.set(false)
    });
  }

  toggleAddForm() {
    this.showAddForm.update(v => !v);
    if (!this.showAddForm()) this.addBookForm.reset();
  }

  onAddBook() {
    if (this.addBookForm.invalid) return;
    this.loading.set(true);
    this.bookService.createBook(this.addBookForm.value as Partial<Book>).subscribe({
      next: () => {
        this.toastService.success('Book registered successfully.');
        this.loadBooks();
        this.addBookForm.reset();
        this.showAddForm.set(false);
      },
      error: (err) => {
        this.toastService.error(err.error?.message || 'Failed to register book.');
        this.loading.set(false);
      }
    });
  }
}

