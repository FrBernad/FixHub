let contactForm = document.getElementById("contactForm")
let processing = false;
let contactFormButton = document.getElementById("contactFormButton");
contactFormButton.addEventListener("click", () => {
    if(processing){
        return;
    }
    processing=true;
    contactFormButton.disabled = true;
    contactForm.submit();
    processing=false;
})