import { Component, Input } from '@angular/core';
import { OrderBy, PostsService } from '../../services/posts.service';
import { PostCardComponent } from '../post-card/post-card.component';
import { AsyncPipe, NgIf } from '@angular/common';
import { take } from 'rxjs';
import { Post } from '../../models/post';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'mhn-posts-list',
  standalone: true,
  imports: [PostCardComponent, AsyncPipe, NgIf],
  providers: [PostsService],
  templateUrl: './posts-list.component.html',
  styleUrl: './posts-list.component.scss',
})
export class PostsListComponent {
  @Input() orderBy: OrderBy = 'new';

  posts: Post[] = [];

  constructor(
    private route: ActivatedRoute,
    private postsService: PostsService
  ) {
    this.route.data.subscribe((data) => {
      this.orderBy = data['orderBy'] || 'new';
    });

    this.postsService
      .getPosts(this.orderBy)
      .pipe(take(1))
      .subscribe((posts) => {
        this.posts = posts;
      });
  }
}
