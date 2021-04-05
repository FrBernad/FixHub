let navbar = document.getElementById("navbar");
let height = navbar.offsetHeight;
document.addEventListener("scroll", e => {
        let scrolled = document.scrollingElement.scrollTop;
        if (scrolled > height) {
            navbar.classList.remove("navbarTop");
            navbar.classList.add("navbarScrolled");
        } else {
            navbar.classList.remove("navbarScrolled");
            navbar.classList.add("navbarTop");
        }
    }
);