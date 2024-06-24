import { Component, OnInit } from '@angular/core';
import { PostsService } from '../../services/posts.service';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'mhn-create-post',
  standalone: true,
  imports: [ReactiveFormsModule, RouterModule],
  providers: [PostsService],
  templateUrl: './create-post.component.html',
  styleUrl: './create-post.component.scss',
})
export class CreatePostComponent implements OnInit {
  postForm: FormGroup = new FormGroup({});

  constructor(private postsService: PostsService, private router: Router) {}

  ngOnInit() {
    this.postForm = new FormGroup({
      text: new FormControl(null, Validators.required),
    });
  }

  createPost() {
    if (this.postForm.valid) {
      this.postsService
        .createPost(this.postForm.value.text)
        .subscribe(() => this.router.navigate(['/']));
    }
  }
}
