import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth-guard';

export const routes: Routes = [
  { 
    path: 'login', 
    loadComponent: () => import('./features/auth/login/login').then(m => m.LoginComponent) 
  },
  {
    path: '',
    loadComponent: () => import('./layout/main-layout/main-layout').then(m => m.MainLayoutComponent),
    canActivate: [authGuard],
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { 
        path: 'dashboard', 
        loadComponent: () => import('./features/dashboard/dashboard').then(m => m.DashboardComponent) 
      },
      { 
        path: 'books', 
        loadComponent: () => import('./features/books/book-catalog/book-catalog').then(m => m.BookCatalogComponent) 
      },
      { 
        path: 'books/:id', 
        loadComponent: () => import('./features/books/book-detail/book-detail').then(m => m.BookDetailComponent) 
      },
      { 
        path: 'members', 
        loadComponent: () => import('./features/members/member-management/member-management').then(m => m.MemberManagementComponent),
        data: { role: 'ROLE_LIBRARIAN' }
      },
      { 
        path: 'loans', 
        loadComponent: () => import('./features/loans/my-loans/my-loans').then(m => m.MyLoansComponent) 
      }
    ]
  },
  { path: '**', redirectTo: 'dashboard' }
];
