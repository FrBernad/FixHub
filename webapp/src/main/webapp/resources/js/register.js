let registerForm = document.getElementById("registerForm")
let processing = false;
let registerFormButton = document.getElementById("registerFormButton");
registerFormButton.addEventListener("click", () => {
    if(processing){
        return;
    }
    processing=true;
    registerFormButton.disabled = true;
    registerForm.submit();
    processing=false;
})

function togglePassword() {
    let password = document.getElementById("password");
    let confirmPassword = document.getElementById("confirmPassword");
    if (password.type === "password" && confirmPassword.type === "password") {
        password.type = "text";
        confirmPassword.type = 'text';
    } else {
        password.type = "password";
        confirmPassword.type = "password";
    }
}