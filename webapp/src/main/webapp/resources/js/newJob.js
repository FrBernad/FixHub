let jobForm = document.getElementById("jobForm")
let processing = false;
let jobFormButton = document.getElementById("jobFormButton");
jobFormButton.addEventListener("click", () => {
    if (processing) {
        return;
    }
    processing = true;
    jobFormButton.disabled = true;
    jobForm.submit();
    processing = false;
})