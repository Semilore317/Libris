import { Component, inject, computed } from '@angular/core';
import { AuthService } from '../../core/services/auth';

@Component({
  selector: 'app-topnav',
  standalone: true,
  imports: [],
  templateUrl: './topnav.html',
  styleUrl: './topnav.css',
})
export class TopnavComponent {
  private authService = inject(AuthService);
  
  username = computed(() => this.authService.currentUser()?.username);
  role = computed(() => this.authService.currentUser()?.roles[0]?.replace('ROLE_', ''));
}
