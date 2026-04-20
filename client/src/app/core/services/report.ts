import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

export interface DashboardSummary {
  totalBooks: number;
  availableBooks: number;
  totalMembers: number;
  activeLoans: number;
}

@Injectable({
  providedIn: 'root'
})
export class ReportService {
  private readonly baseUrl = `${environment.baseUrl}/v1/reports`;

  constructor(private http: HttpClient) {}

  getDashboardSummary(): Observable<DashboardSummary> {
    return this.http.get<DashboardSummary>(`${this.baseUrl}/dashboard`);
  }
}
