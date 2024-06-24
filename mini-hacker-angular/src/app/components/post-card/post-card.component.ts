import { Component, HostBinding, Input } from '@angular/core';
import { Post } from '../../models/post';
import { JsonPipe } from '@angular/common';
import { PostsService } from '../../services/posts.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'mhn-post-card',
  standalone: true,
  imports: [JsonPipe],
  providers: [PostsService],
  templateUrl: './post-card.component.html',
  styleUrl: './post-card.component.scss',
})
export class PostCardComponent {
  @Input() post?: Post;

  @Input() index?: number;

  @HostBinding('id') get id() {
    return this.post?.id;
  }

  constructor(private postsService: PostsService) {}

  private vote(action: (id: string) => Observable<Post>) {
    if (!this.post) {
      return;
    }

    action(this.post.id).subscribe((post) => {
      if (this.post) {
        this.post.score = post.score;
      }
    });
  }

  upvote() {
    this.vote(this.postsService.upvote.bind(this.postsService));
  }

  downvote() {
    this.vote(this.postsService.downvote.bind(this.postsService));
  }
}
