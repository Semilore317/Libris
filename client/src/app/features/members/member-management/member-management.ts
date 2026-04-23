import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MemberService, Member } from '../../../core/services/member';
import { ButtonComponent } from '../../../shared/components/button/button';

@Component({
  selector: 'app-member-management',
  standalone: true,
  imports: [CommonModule, ButtonComponent],
  templateUrl: './member-management.html',
  styleUrl: './member-management.css',
})
export class MemberManagementComponent {
  private memberService = inject(MemberService);
  
  members = signal<Member[]>([]);
  loading = signal(true);

  constructor() {
    this.loadMembers();
  }

  loadMembers() {
    this.loading.set(true);
    this.memberService.getAllMembers().subscribe({
      next: (data) => {
        this.members.set(data);
        this.loading.set(false);
      },
      error: () => this.loading.set(false)
    });
  }

  editMember(id: number) {
    alert(`Editing functionality for member ID ${id} is not yet implemented.`);
  }

  deleteMember(id: number) {
    if (confirm('Are you sure you want to delete this member?')) {
      this.memberService.deleteMember(id).subscribe({
        next: () => this.loadMembers()
      });
    }
  }
}
