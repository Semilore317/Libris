import { Component, inject, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { BookService, Book } from '../../../core/services/book';
import { AuthService } from '../../../core/services/auth';
import { ButtonComponent } from '../../../shared/components/button/button';

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
  private fb = inject(FormBuilder);
  
  books = signal<Book[]>([]);
  loading = signal(false);
  showAddForm = signal(false);
  isLibrarian = computed(() => this.authService.hasRole('ROLE_LIBRARIAN'));

  addBookForm = this.fb.group({
    title: ['', [Validators.required]],
    author: ['', [Validators.required]],
    isbn: ['', [Validators.required]],
    category: ['', [Validators.required]],
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
    const query = (event.target as HTMLInputElement).value;
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
  }

  onAddBook() {
    if (this.addBookForm.valid) {
      this.loading.set(true);
      const newBook = this.addBookForm.value as Partial<Book>;
      
      this.bookService.createBook(newBook).subscribe({
        next: () => {
          this.loadBooks();
          this.addBookForm.reset();
          this.showAddForm.set(false);
        },
        error: (err) => {
          console.error('Error adding book:', err);
          this.loading.set(false);
        }
      });
    }
  }
}
