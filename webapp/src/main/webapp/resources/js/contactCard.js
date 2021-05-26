document.addEventListener("DOMContentLoaded", () => {

    let acceptJobBtn = document.getElementById("acceptJobBtn");
    let rejectJobBtn = document.getElementById("rejectJobBtn");
    let completedJobBtn = document.getElementById("completedJobBtn");

    let acceptJobForm = document.getElementById("acceptJobForm");
    let rejectJobForm = document.getElementById("rejectJobForm");
    let completedJobForm = document.getElementById("completedJobForm");

    let processing = false;
    if (acceptJobForm !== null) {
        acceptJobBtn.addEventListener("click", () => {
            if (processing) {
                return;
            }
            processing = true;
            acceptJobBtn.disabled = true;
            acceptJobForm.submit();
            processing = false;
        });
    }

    if (rejectJobForm !== null) {
        rejectJobBtn.addEventListener("click", () => {
            if (processing) {
                return;
            }
            processing = true;
            rejectJobBtn.disabled = true;
            rejectJobForm.submit();
            processing = false;
        })
    }
     if (completedJobForm !== null) {
        completedJobBtn.addEventListener("click", () => {
            if (processing) {
                return;
            }
            processing = true;
            completedJobBtn.disabled = true;
            completedJobForm.submit();
            processing = false;
        })
    }
})
