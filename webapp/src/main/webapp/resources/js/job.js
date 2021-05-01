document.addEventListener("DOMContentLoaded", () => {

    let reviewForm = document.getElementById("reviewForm");
    let reviewFormTextArea = document.getElementById("reviewFormTextArea");
    let processing = false;
    let reviewFormButton = document.getElementById("reviewFormButton");
    let reviewFormCloseButton = document.getElementById("reviewFormCloseButton");
    let reviewModal = document.getElementById("newReview");


    let reviewsPagination = document.getElementById("pagination");
    if (reviewsPagination !== null) {

        let indexs = document.getElementsByClassName("index");
        let reviewPaginationForm = document.getElementById("reviewsPaginationForm");
        let formPageInput = document.getElementById("pageInput");

        let prevBtn = document.getElementById("prev");
        let nextBtn = document.getElementById("next");
        let paginationModalInput = document.getElementById("paginationModalInput")
        let currentPage = reviewsPagination.dataset.page;

        let reviewsModal = $('#reviewsModal');

        prevBtn.addEventListener("click", () => {
            formPageInput.setAttribute("value", (parseInt(currentPage) - 1).toString());
            paginationModalInput.setAttribute("value", "true");
            reviewPaginationForm.submit();
        });

        nextBtn.addEventListener("click", () => {
            formPageInput.setAttribute("value", (parseInt(currentPage) + 1).toString());
            paginationModalInput.setAttribute("value", "true");
            reviewPaginationForm.submit();
        });

        for (const index of indexs) {
            index.addEventListener("click", () => {
                const pageNum = index.dataset.index - 1;
                formPageInput.setAttribute("value", pageNum.toString());
                paginationModalInput.setAttribute("value", "true");
                reviewPaginationForm.submit();
            })
        }

        if (reviewsModal.data("paginationmodal") === "true") {
            reviewsModal.modal('show');
        }

        reviewsModal.on('hidden.bs.modal', () => {
            paginationModalInput.setAttribute("value", "false");
        })

    }

    if ((document.getElementById("carousel") !== null)) {
        document.getElementById("carousel").getElementsByTagName("div")[0].className += " active";
    }

    if (reviewModal != null) {

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
    }

})

