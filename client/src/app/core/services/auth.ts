import { Injectable, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { tap } from 'rxjs/operators';
import { Observable } from 'rxjs';

interface User {
  username: string;
  roles: string[];
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly baseUrl = `${environment.baseUrl}/auth`;
  
  private userSignal = signal<User | null>(this.getUserFromStorage());
  readonly currentUser = computed(() => this.userSignal());
  readonly isAuthenticated = computed(() => !!this.userSignal());
  readonly isLibrarian = computed(() => this.userSignal()?.roles.includes('ROLE_LIBRARIAN') ?? false);

  constructor(private http: HttpClient) {}

  hasRole(role: string): boolean {
    return this.userSignal()?.roles.includes(role) ?? false;
  }

  login(credentials: { username: string; password: any }): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/login`, credentials).pipe(
      tap(response => {
        localStorage.setItem('token', response.token);
        const user: User = { 
          username: credentials.username, 
          roles: response.roles 
        };
        localStorage.setItem('user', JSON.stringify(user));
        this.userSignal.set(user);
      })
    );
  }

  register(data: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/register`, data);
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    this.userSignal.set(null);
  }

  private getUserFromStorage(): User | null {
    const userStr = localStorage.getItem('user');
    try {
      return userStr ? JSON.parse(userStr) : null;
    } catch {
      return null;
    }
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }
}
