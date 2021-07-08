import {Component, OnInit} from '@angular/core';
import {animate, group, query, stagger, state, style, transition, trigger} from "@angular/animations";
import {User} from "../models/User";
import {Job} from "../models/Job";

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
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

  //FIXME: BORRAR TODA ESTA PARTE DE JOB Y USER
  provider: User = {
    id: 2,
    name: 'Agus',
    surname: 'Manfredi',
    email: 'agus@yopmail.com',
    phoneNumber: '+5491112345678',
    state: 'Buenos Aires',
    city: 'Adrogue',
    profileImage: 1,
    coverImage: 2,
    following: [],
    followers: [],
    providerDetails: {
      location: {
        cities: [{id: 1, name: 'Burzaco'}],
        state: {id: 1, name: 'Buenos Aires'}
      },
      schedule: {
        startTime: new Date(),
        endTime: new Date()
      }
    },
  }

  job: Job = {
    id: 1, description: 'asda', jobProvided: 'dasda',
    category: 'CARPINTERO', price: 121, totalRatings: 0,
    averageRating: 0, images: [], reviews: [],
    provider: this.provider,
    paused: false,
    thumbnailId: 1
  };

  jobs = [
    this.job,this.job,this.job,this.job
  ];
  constructor() {
  }

  ngOnInit(): void {
  }

}
