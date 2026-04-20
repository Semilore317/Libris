import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { BookService, Book } from '../../../core/services/book';
import { CardComponent } from '../../../shared/components/card/card';
import { ButtonComponent } from '../../../shared/components/button/button';

@Component({
  selector: 'app-book-catalog',
  standalone: true,
  imports: [CommonModule, RouterLink, CardComponent, ButtonComponent],
  templateUrl: './book-catalog.html',
  styleUrl: './book-catalog.css',
})
export class BookCatalogComponent {
  private bookService = inject(BookService);
  
  books = signal<Book[]>([]);
  loading = signal(false);

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
}
