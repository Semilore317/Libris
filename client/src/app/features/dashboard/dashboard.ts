import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CardComponent } from '../../shared/components/card/card';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, CardComponent],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class DashboardComponent {
  stats = signal([
    { label: 'Total Inventory', value: '1,424', change: '+12 this week' },
    { label: 'Active Members', value: '892', change: '+4 this week' },
    { label: 'Active Loans', value: '156', change: '-2 this week' },
    { label: 'Pending Reservations', value: '38', change: '+8 this week' }
  ]);

  recentActivities = signal([
    { action: 'Book Returned', target: 'The Great Gatsby', user: 'frodo.baggins', time: '2 mins ago' },
    { action: 'New Loan', target: 'Clean Code', user: 'sarah.connor', time: '15 mins ago' },
    { action: 'Member Joined', target: 'New Account', user: 'samwise.gamgee', time: '1 hour ago' },
    { action: 'Reservation', target: 'Angular Pro', user: 'frodo.baggins', time: '3 hours ago' }
  ]);
}
