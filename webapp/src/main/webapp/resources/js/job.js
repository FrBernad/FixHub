let reviewForm = document.getElementById("reviewForm")
let processing = false;
let reviewFormButton = document.getElementById("reviewFormButton");
reviewFormButton.addEventListener("click", () => {
    if(processing){
        return;
    }
    processing=true;
    reviewFormButton.disabled = true;
    reviewForm.submit();
    processing=false;
})