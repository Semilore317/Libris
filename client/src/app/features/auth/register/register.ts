import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth';
import { ButtonComponent } from '../../../shared/components/button/button';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, ButtonComponent],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class RegisterComponent {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);

  loading = signal(false);
  error = signal<string | null>(null);

  registerForm = this.fb.group({
    firstName: ['', [Validators.required]],
    lastName: ['', [Validators.required]],
    email: ['', [Validators.required, Validators.email]],
    username: ['', [Validators.required]],
    password: ['', [Validators.required, Validators.minLength(4)]],
  });

  onSubmit() {
    if (this.registerForm.valid) {
      this.loading.set(true);
      this.error.set(null);

      this.authService.register(this.registerForm.value).subscribe({
        next: () => {
          this.router.navigate(['/login'], { 
            queryParams: { registered: 'true' } 
          });
        },
        error: (err) => {
          this.error.set(err.error?.message || 'REGISTRATION FAILURE');
          this.loading.set(false);
        }
      });
    }
  }
}
