let reviewForm = document.getElementById("reviewForm");
let reviewFormTextArea = document.getElementById("reviewFormTextArea");
let processing = false;
let reviewFormButton = document.getElementById("reviewFormButton");
let reviewFormCloseButton = document.getElementById("reviewFormCloseButton");
reviewFormButton.addEventListener("click", () => {
    if (processing) {
        return;
    }
    if (reviewFormTextArea.value === "") {
        reviewFormTextArea.classList.add("form-control", "is-invalid");
        return;
    }
    processing = true;
    reviewFormButton.disabled = true;
    reviewForm.submit();
    processing = false;
})

reviewFormCloseButton.addEventListener("click", () => {
    reviewFormTextArea.classList.remove("is-invalid");
})