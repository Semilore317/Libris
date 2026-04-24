import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ToastService } from '../../../core/services/toast';

@Component({
  selector: 'app-toast',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="fixed bottom-8 right-8 z-[100] flex flex-col gap-4 pointer-events-none">
      @for (toast of toastService.toasts(); track toast.id) {
        <div 
          class="minimal-border p-4 bg-white shadow-xl animate-in slide-in-from-right duration-300 pointer-events-auto flex items-center gap-4 min-w-[300px]"
          [ngClass]="{
            'border-black': toast.type === 'info',
            'border-green-500': toast.type === 'success',
            'border-red-500': toast.type === 'error'
          }"
        >
          <div class="flex-1">
            <div class="text-[10px] font-bold uppercase tracking-widest opacity-30 mb-1">
              {{ toast.type }}
            </div>
            <div class="text-sm font-medium">{{ toast.message }}</div>
          </div>
          <button (click)="toastService.remove(toast.id)" class="text-xs font-bold opacity-30 hover:opacity-100 transition-opacity uppercase tracking-tighter">
            Close
          </button>
        </div>
      }
    </div>
  `,
  styles: [`
    :host { display: block; }
  `]
})
export class ToastComponent {
  toastService = inject(ToastService);
}
