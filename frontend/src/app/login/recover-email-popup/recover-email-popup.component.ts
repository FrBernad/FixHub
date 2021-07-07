import {Component, OnInit, Output, EventEmitter} from '@angular/core';

@Component({
  selector: 'app-recover-email-popup',
  templateUrl: './recover-email-popup.component.html',
  styleUrls: ['./recover-email-popup.component.scss']
})
export class RecoverEmailPopupComponent implements OnInit {

  @Output() close = new EventEmitter<void>();

  constructor() {
  }

  ngOnInit(): void {
  }

  onClose() {
    this.close.emit();
  }

}
