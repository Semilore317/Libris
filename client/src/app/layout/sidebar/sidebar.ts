import { Component, inject, computed } from '@angular/core';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../core/services/auth';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css',
})
export class SidebarComponent {
  private authService = inject(AuthService);
  private router = inject(Router);

  isLibrarian = computed(() => this.authService.hasRole('ROLE_LIBRARIAN'));

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
