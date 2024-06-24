import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'mhn-top-bar',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './top-bar.component.html',
  styleUrl: './top-bar.component.scss',
})
export class TopBarComponent {}
