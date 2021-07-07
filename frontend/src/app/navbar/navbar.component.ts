import {Component, ElementRef, HostListener, OnInit, Renderer2, ViewChild, ViewEncapsulation} from '@angular/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {

  @ViewChild('backNavbar') backNavbar: ElementRef;
  @ViewChild('navbar') navbar: ElementRef;

  constructor(private renderer: Renderer2) {
  }

  ngOnInit(): void {
  }

  @HostListener('window:scroll') onScrollHost(): void {
    let scrolled = document.scrollingElement.scrollTop;
    if (scrolled > 0) {
      this.renderer.removeClass(this.backNavbar.nativeElement, "d-none");
      this.renderer.addClass(this.backNavbar.nativeElement, "d-block");
      this.renderer.removeClass(this.navbar.nativeElement, "navbarTop");
      this.renderer.addClass(this.navbar.nativeElement, "navbarScrolled");
    } else {
      this.renderer.removeClass(this.backNavbar.nativeElement, "d-block");
      this.renderer.addClass(this.backNavbar.nativeElement, "d-none");
      this.renderer.removeClass(this.navbar.nativeElement, "navbarScrolled");
      this.renderer.addClass(this.navbar.nativeElement, "navbarTop");
    }
  }


}
