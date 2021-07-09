import { Component, OnInit } from '@angular/core';
import { OrderOptionModel } from '../models/orderOption.model';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  activeTab: string = 'dashboard';

  orderOptions = Object.keys(OrderOptionModel).filter((item) => {
    return isNaN(Number(item));
  });


  constructor() { }

  ngOnInit(): void {
  }

  changeTab(tab: string) {
    this.activeTab = tab;
  }

}
