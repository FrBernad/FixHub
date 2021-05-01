document.addEventListener("DOMContentLoaded", () => {
    let editFormButton = document.getElementById("editFormButton");
    let pauseInput = document.getElementById("paused");
    let pauseRadio = document.getElementById("editPaused");
    let jobForm = document.getElementById("jobForm");
    pauseRadio.checked = pauseInput.value;

    editFormButton.addEventListener("click", () => {
        pauseInput.value = pauseRadio.checked;
        jobForm.submit();
    })
})