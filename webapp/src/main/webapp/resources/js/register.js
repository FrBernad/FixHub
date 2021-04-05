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