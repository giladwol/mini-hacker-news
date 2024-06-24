import { Injectable } from '@angular/core';
import { Post } from '../models/post';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';

export type OrderBy = 'top' | 'new';

@Injectable({
  providedIn: 'root',
})
export class PostsService {
  private baseUrl = 'http://localhost:8080/api/posts';

  constructor(private httpClient: HttpClient) {}

  getPosts(orderBy: OrderBy): Observable<Post[]> {
    return this.httpClient.get<Post[]>(this.baseUrl + '/' + orderBy);
  }

  createPost(text: string): Observable<Post> {
    return this.httpClient.post<Post>(this.baseUrl, { text });
  }

  upvote(id: string): Observable<Post> {
    return this.httpClient.post<Post>(this.baseUrl + '/' + id + '/upvote', {});
  }

  downvote(id: string): Observable<Post> {
    return this.httpClient.post<Post>(
      this.baseUrl + '/' + id + '/downvote',
      {}
    );
  }

  getMockPosts(orderBy: OrderBy): Observable<Post[]> {
    if (orderBy === 'new') {
      return of([
        {
          id: '1',
          text: 'This is the first post',
          createdAt: new Date(),
          score: 0,
        },
        {
          id: '2',
          text: 'This is the second post',
          createdAt: new Date(),
          score: 50,
        },
        {
          id: '3',
          text: 'This is the third post',
          createdAt: new Date(),
          score: 100,
        },
      ]);
    } else {
      return of([
        {
          id: '4',
          text: 'This is the fourth post',
          createdAt: new Date(),
          score: 98,
        },
        {
          id: '5',
          text: 'This is the fifth post',
          createdAt: new Date(),
          score: 90,
        },
        {
          id: '6',
          text: 'This is the sixth post',
          createdAt: new Date(),
          score: 80,
        },
      ]);
    }
  }
}
