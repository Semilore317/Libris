import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { MemberService, Member } from '../../../core/services/member';
import { ToastService } from '../../../core/services/toast';
import { ButtonComponent } from '../../../shared/components/button/button';

@Component({
  selector: 'app-member-management',
  standalone: true,
  imports: [CommonModule, ButtonComponent, ReactiveFormsModule],
  templateUrl: './member-management.html',
  styleUrl: './member-management.css',
})
export class MemberManagementComponent {
  private memberService = inject(MemberService);
  private toastService = inject(ToastService);
  private fb = inject(FormBuilder);

  members = signal<Member[]>([]);
  loading = signal(true);
  showAddForm = signal(false);
  editingMemberId = signal<number | null>(null);
  confirmDeleteId = signal<number | null>(null);

  addMemberForm = this.fb.group({
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    phoneNumber: [''],
    username: ['', Validators.required],
    password: ['', [Validators.required, Validators.minLength(6)]],
  });

  editMemberForm = this.fb.group({
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    phoneNumber: [''],
  });

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

  toggleAddForm() {
    this.showAddForm.update(v => !v);
    if (!this.showAddForm()) this.addMemberForm.reset();
  }

  onAddMember() {
    if (this.addMemberForm.invalid) return;
    this.memberService.addMember(this.addMemberForm.value as any).subscribe({
      next: () => {
        this.toastService.success('Member registered successfully.');
        this.loadMembers();
        this.addMemberForm.reset();
        this.showAddForm.set(false);
      },
      error: (err: any) => this.toastService.error(err.error?.message || 'Failed to add member.')
    });
  }

  startEdit(member: Member) {
    this.editingMemberId.set(member.id);
    this.editMemberForm.patchValue({
      firstName: member.firstName,
      lastName: member.lastName,
      email: member.email,
      phoneNumber: member.phoneNumber ?? '',
    });
  }

  cancelEdit() {
    this.editingMemberId.set(null);
    this.editMemberForm.reset();
  }

  saveEdit() {
    const id = this.editingMemberId();
    if (id == null || this.editMemberForm.invalid) return;
    this.memberService.updateMember(id, this.editMemberForm.value as any).subscribe({
      next: () => {
        this.toastService.success('Member updated.');
        this.editingMemberId.set(null);
        this.loadMembers();
      },
      error: (err: any) => this.toastService.error(err.error?.message || 'Failed to update member.')
    });
  }

  promptDelete(id: number) {
    this.confirmDeleteId.set(id);
  }

  cancelDelete() {
    this.confirmDeleteId.set(null);
  }

  confirmDelete() {
    const id = this.confirmDeleteId();
    if (id == null) return;
    this.confirmDeleteId.set(null);
    this.memberService.deleteMember(id).subscribe({
      next: () => {
        this.toastService.success('Member removed.');
        this.loadMembers();
      },
      error: (err: any) => this.toastService.error(err.error?.message || 'Failed to delete member.')
    });
  }
}
