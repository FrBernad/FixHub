import {Component, Input, OnInit, Output, EventEmitter} from '@angular/core';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.scss']
})

export class PaginationComponent implements OnInit {

  @Output() changePage = new EventEmitter<number>();
  @Input() totalPages;
  @Input() currentPage;

  constructor() {
  }

  ngOnInit() {
  }

  setPage(page: number) {
    this.changePage.emit(page);
  }

  numSequence(n: number): Array<number> {
    return Array(n).fill(1).map((x, i) => i);
  }

}
