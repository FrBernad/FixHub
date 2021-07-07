import {Component, OnInit} from '@angular/core';
import {animate, group, query, stagger, state, style, transition, trigger} from "@angular/animations";

@Component({
  selector: 'app-landing-page',
  templateUrl: './lading-page.component.html',
  styleUrls: ['./landing-page.component.scss'],
  animations: [
    trigger('fadeIn', [
      transition('void => *', [
        state('in', style({
          transform: 'translateX(0)', opacity: 1
        })),
        query('.slogan, .searchBar, .categoryBtn', [
          style({
            transform: 'translateX(0)', opacity: 0
          }),
          stagger('0.3s', [
            style({
              transform: 'translateX(-100px)',
              opacity: 0
            }),
            animate('0.5s 0.1s ease')
          ])
        ]),
      ]),
    ])
  ]
})
export class LandingPageComponent implements OnInit {

  categories = ['herrero', 'ayudante', 'ayudante', 'ayudante'];

  constructor() {
  }

  ngOnInit(): void {
  }

}
