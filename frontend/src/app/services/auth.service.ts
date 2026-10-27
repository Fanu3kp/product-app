import { Injectable, APP_INITIALIZER } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject, of } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { AuthResponse } from '../models';
import { environment } from '../../environments/environment';

const API_URL = `${environment.apiUrl}/api/auth`;

export interface LoginRequest {
  username: string;
  password: string;
}

export interface RegisterRequest {
  username: string;
  password: string;
  email: string;
  role: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private currentUserSubject: BehaviorSubject<AuthResponse>;
  public currentUser: Observable<AuthResponse>;

  constructor(private http: HttpClient) {
    const stored = localStorage.getItem('currentUser');
    this.currentUserSubject = new BehaviorSubject<AuthResponse>(stored ? JSON.parse(stored) : null);
    this.currentUser = this.currentUserSubject.asObservable();
  }

  autoLoginAsAdmin(): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${API_URL}/login`, { username: 'admin', password: 'admin123' })
      .pipe(tap(user => {
        localStorage.setItem('currentUser', JSON.stringify(user));
        this.currentUserSubject.next(user);
      }));
  }

  public get currentUserValue(): AuthResponse {
    return this.currentUserSubject.value;
  }

  login(username: string, password: string): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${API_URL}/login`, { username, password })
      .pipe(tap(user => {
        localStorage.setItem('currentUser', JSON.stringify(user));
        this.currentUserSubject.next(user);
      }));
  }

  register(username: string, password: string, email: string, role: string): Observable<any> {
    return this.http.post(`${API_URL}/register`, { username, password, email, role });
  }

  logout(): void {
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null as any);
  }

  isAuthenticated(): boolean {
    return !!this.currentUserValue;
  }

  isAdmin(): boolean {
    return this.currentUserValue?.role === 'ADMIN';
  }
}

export function initAuth(authService: AuthService) {
  return () => authService.autoLoginAsAdmin().pipe(catchError(() => of(null))).toPromise();
}
