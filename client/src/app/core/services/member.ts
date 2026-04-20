import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

export interface Member {
  id: number;
  fullName: string;
  email: string;
  phoneNumber: string;
  membershipNumber: string;
}

@Injectable({
  providedIn: 'root'
})
export class MemberService {
  private readonly baseUrl = `${environment.baseUrl}/v1/members`;

  constructor(private http: HttpClient) {}

  getAllMembers(): Observable<Member[]> {
    return this.http.get<Member[]>(this.baseUrl);
  }

  getMemberById(id: number): Observable<Member> {
    return this.http.get<Member>(`${this.baseUrl}/${id}`);
  }

  addMember(member: Partial<Member>): Observable<Member> {
    return this.http.post<Member>(this.baseUrl, member);
  }

  deleteMember(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
