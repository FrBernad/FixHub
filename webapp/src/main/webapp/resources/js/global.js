let navbar = document.getElementById("navbar");
let navFix = document.getElementById("navFix");
document.addEventListener("scroll", e => {
        let scrolled = document.scrollingElement.scrollTop;
        if (scrolled > 0) {
            navFix.classList.remove("d-none");
            navFix.classList.add("d-block");
            navbar.classList.remove("navbarTop");
            navbar.classList.add("navbarScrolled");
        } else {
            navFix.classList.remove("d-block");
            navFix.classList.add("d-none");
            navbar.classList.remove("navbarScrolled");
            navbar.classList.add("navbarTop");
        }
    }
);



