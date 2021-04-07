window.addEventListener("load", () => {

    let reviewForm = document.getElementById("reviewForm");
    let reviewFormTextArea = document.getElementById("reviewFormTextArea");
    let processing = false;
    let reviewFormButton = document.getElementById("reviewFormButton");
    let reviewFormCloseButton = document.getElementById("reviewFormCloseButton");
    let reviewModal = document.getElementById("newReview");

    if (reviewModal.dataset.error) {
        $('#newReview').modal('show');
    }

    reviewFormButton.addEventListener("click", () => {
        if (processing) {
            return;
        }
        processing = true;
        reviewFormButton.disabled = true;
        reviewForm.submit();
        processing = false;
    })

    reviewFormCloseButton.addEventListener("click", () => {
        reviewFormTextArea.classList.remove("is-invalid");
        let error = document.getElementById("description.errors");
        if (error !== null) {
            error.remove();
        }
    })
})

