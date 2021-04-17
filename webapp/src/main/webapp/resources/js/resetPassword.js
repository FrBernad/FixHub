let resetForm = document.getElementById("passReset")
let processing = false;
let resetBtn = document.getElementById("sumbitBtn");
resetBtn.addEventListener("click", () => {
    if(processing){
        return;
    }
    processing=true;
    resetBtn.disabled = true;
    resetForm.submit();
    processing=false;
})

let eyeBtn1 = document.getElementById("passwordEye1");
let passwordInput1 = document.getElementById("password1");
let eye1 = document.getElementById("eye1");
let visible1 = false;

eyeBtn1.addEventListener("click", () => {
    if (visible1) {
        visible1 = false;
        passwordInput1.setAttribute("type", "password");
    } else {
        visible1 = true;
        passwordInput1.setAttribute("type", "text");
    }
    eye1.classList.toggle("fa-eye-slash");
    eye1.classList.toggle("fa-eye");
});

let eyeBtn2 = document.getElementById("passwordEye2");
let passwordInput2 = document.getElementById("password2");
let eye2 = document.getElementById("eye2");
let visible2 = false;

eyeBtn2.addEventListener("click", () => {
    if (visible2) {
        visible2 = false;
        passwordInput2.setAttribute("type", "password");
    } else {
        visible2 = true;
        passwordInput2.setAttribute("type", "text");
    }
    eye2.classList.toggle("fa-eye-slash");
    eye2.classList.toggle("fa-eye");
});