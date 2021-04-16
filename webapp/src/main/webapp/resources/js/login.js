let eyeBtn = document.getElementById("passwordEye");
let passwordInput = document.getElementById("password");
let eye = document.getElementById("eye");
let visible = false;

eyeBtn.addEventListener("click", () => {
    if (visible) {
        visible = false;
        passwordInput.setAttribute("type", "password");
    } else {
        visible = true;
        passwordInput.setAttribute("type", "text");
    }
    eye.classList.toggle("fa-eye-slash");
    eye.classList.toggle("fa-eye");
});