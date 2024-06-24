import { Routes } from '@angular/router';
import { CreatePostComponent } from './components/create-post/create-post.component';
import { PostsListComponent } from './components/posts-list/posts-list.component';

export const routes: Routes = [
  {
    path: '',
    component: PostsListComponent,
  },
  {
    path: 'top',
    component: PostsListComponent,
    data: { orderBy: 'top' },
  },
  {
    path: 'create',
    component: CreatePostComponent,
  },
];
