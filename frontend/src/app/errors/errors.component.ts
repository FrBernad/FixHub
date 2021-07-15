import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-errors',
  templateUrl: './errors.component.html',
  styleUrls: ['./errors.component.scss']
})
export class ErrorsComponent implements OnInit {

  errors:string ='Not Found';
  code: number = 404;

  constructor() { }

  ngOnInit(): void {
  }

}
