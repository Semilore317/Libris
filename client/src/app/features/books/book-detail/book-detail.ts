import { Component, inject, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { BookService, Book } from '../../../core/services/book';
import { MemberService, Member } from '../../../core/services/member';
import { AuthService } from '../../../core/services/auth';
import { LoanService } from '../../../core/services/loan';
import { ReservationService } from '../../../core/services/reservation';
import { ToastService } from '../../../core/services/toast';
import { ButtonComponent } from '../../../shared/components/button/button';

@Component({
  selector: 'app-book-detail',
  standalone: true,
  imports: [CommonModule, RouterLink, ButtonComponent, FormsModule],
  templateUrl: './book-detail.html',
  styleUrl: './book-detail.css',
})
export class BookDetailComponent {
  private route = inject(ActivatedRoute);
  private bookService = inject(BookService);
  private memberService = inject(MemberService);
  private authService = inject(AuthService);
  private loanService = inject(LoanService);
  private reservationService = inject(ReservationService);
  private toastService = inject(ToastService);

  book = signal<Book | null>(null);
  loading = signal(true);
  copyCount = signal(1);
  memberSearchQuery = signal('');
  foundMember = signal<Member | null>(null);
  checkoutLoading = signal(false);
  
  isLibrarian = computed(() => this.authService.hasRole('ROLE_LIBRARIAN'));
  isMember = computed(() => this.authService.hasRole('ROLE_MEMBER'));

  constructor() {
    this.loadBook();
  }

  loadBook() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (!id) return;

    this.bookService.getBookById(id).subscribe({
      next: (data) => {
        this.book.set(data);
        this.loading.set(false);
      },
      error: () => this.loading.set(false)
    });
  }

  reserveBook() {
    const b = this.book();
    if (!b) return;

    this.reservationService.createReservation(b.id).subscribe({
      next: () => {
        this.toastService.success('Book reserved successfully.');
        this.loadBook();
      },
      error: (err) => this.toastService.error(`Error reserving book: ${err.error?.message || 'Please try again later.'}`)
    });
  }

  borrowBook() {
    const b = this.book();
    if (!b) return;

    this.checkoutLoading.set(true);
    this.loanService.checkoutBook(b.id).subscribe({
      next: () => {
        this.toastService.success('Book borrowed successfully!');
        this.loadBook();
        this.checkoutLoading.set(false);
      },
      error: (err) => {
        this.toastService.error(`Error borrowing book: ${err.error?.message || 'Please try again later.'}`);
        this.checkoutLoading.set(false);
      }
    });
  }

  lookupMember() {
    if (!this.memberSearchQuery()) return;
    this.memberService.getMemberByMembershipNumber(this.memberSearchQuery()).subscribe({
      next: (m) => this.foundMember.set(m),
      error: () => {
        this.toastService.error('Member not found.');
        this.foundMember.set(null);
      }
    });
  }

  checkoutBook() {
    const book = this.book();
    const member = this.foundMember();
    if (!book || !member) return;

    this.checkoutLoading.set(true);
    this.loanService.checkoutBook(book.id, member.id).subscribe({
      next: () => {
        this.toastService.success('Checkout successful!');
        this.loadBook();
        this.foundMember.set(null);
        this.memberSearchQuery.set('');
        this.checkoutLoading.set(false);
      },
      error: (err) => {
        this.toastService.error(`Checkout failed: ${err.error?.message || 'Unknown error'}`);
        this.checkoutLoading.set(false);
      }
    });
  }

  addCopies() {
    const b = this.book();
    if (!b || this.copyCount() < 1) return;

    this.loading.set(true);
    this.bookService.addInstances(b.id, this.copyCount()).subscribe({
      next: () => {
        this.toastService.success(`${this.copyCount()} ${this.copyCount() === 1 ? 'copy' : 'copies'} added successfully.`);
        this.loadBook();
        this.copyCount.set(1);
      },
      error: (err) => {
        this.toastService.error(err.error?.message || 'Failed to add copies.');
        this.loading.set(false);
      }
    });
  }
}
