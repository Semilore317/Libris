import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

export interface Book {
  id: number;
  title: string;
  author: string;
  isbn: string;
  category: string;
  availableCount: number;
  totalCount: number;
}

@Injectable({
  providedIn: 'root'
})
export class BookService {
  private readonly baseUrl = `${environment.baseUrl}/v1/books`;

  constructor(private http: HttpClient) {}

  getAllBooks(): Observable<Book[]> {
    return this.http.get<Book[]>(this.baseUrl);
  }

  getBookById(id: number): Observable<Book> {
    return this.http.get<Book>(`${this.baseUrl}/${id}`);
  }

  searchBooks(query: string): Observable<Book[]> {
    return this.http.get<Book[]>(`${this.baseUrl}/search?query=${query}`);
  }

  createBook(book: Partial<Book>): Observable<Book> {
    return this.http.post<Book>(this.baseUrl, book);
  }

  addInstances(bookId: number, quantity: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/instances`, { bookId, quantity });
  }
}
