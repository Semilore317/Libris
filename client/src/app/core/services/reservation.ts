import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

export interface Reservation {
  id: number;
  book: {
    title: string;
    author: string;
  };
  reservationDate: string;
  status: string;
}

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private readonly baseUrl = `${environment.baseUrl}/v1/reservations`;

  constructor(private http: HttpClient) {}

  createReservation(bookId: number): Observable<Reservation> {
    return this.http.post<Reservation>(`${this.baseUrl}?bookId=${bookId}`, {});
  }

  getMyReservations(): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.baseUrl}/my-reservations`);
  }

  cancelReservation(id: number): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/${id}/cancel`, {});
  }
}
