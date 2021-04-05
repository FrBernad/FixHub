let serviceForm = document.getElementById("serviceForm")
let processing = false;
let serviceFormButton = document.getElementById("serviceFormButton");
serviceFormButton.addEventListener("click", () => {
    if(processing){
        return;
    }
    processing=true;
    serviceFormButton.disabled = true;
    serviceForm.submit();
    processing=false;
})