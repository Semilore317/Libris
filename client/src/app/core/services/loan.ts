import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

export interface Loan {
  id: number;
  bookInstance: {
    book: {
      title: string;
      author: string;
    }
  };
  loanDate: string;
  dueDate: string;
  returnDate: string | null;
  status: string;
}

@Injectable({
  providedIn: 'root'
})
export class LoanService {
  private readonly baseUrl = `${environment.baseUrl}/v1/loans`;

  constructor(private http: HttpClient) {}

  getMyHistory(): Observable<Loan[]> {
    return this.http.get<Loan[]>(`${this.baseUrl}/my-history`);
  }

  checkoutBook(bookInstanceId: number, memberId?: number, dueDate?: string): Observable<Loan> {
    return this.http.post<Loan>(`${this.baseUrl}/checkout`, { bookInstanceId, memberId, dueDate });
  }
}
