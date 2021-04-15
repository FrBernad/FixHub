window.addEventListener("load", () => {

    let jobForm = document.getElementById("jobForm")
    let imagesHolder = document.getElementById("imagesHolder");
    let processing = false;
    let jobFormButton = document.getElementById("jobFormButton");
    let addFileButtom = document.getElementById("addFileButtom");
    let files = [];
    let indexFile = 0;


    jobFormButton.addEventListener("click", () => {
        if (processing) {
            return;
        }
        processing = true;
        jobFormButton.disabled = true;
        jobForm.submit();
        processing = false;
    })

    addFileButtom.addEventListener("click", () => {
        const inputFile = document.createElement("input");
        inputFile.setAttribute("type", "file");
        inputFile.setAttribute("id", "image" + indexFile);
        inputFile.setAttribute("name", "images");
        inputFile.setAttribute("hidden", "true");
        indexFile++;
        inputFile.click();

        imagesHolder.appendChild(inputFile);

        inputFile.addEventListener("change", () => {
            const text = document.createElement("h2");
            text.innerHTML = inputFile.files[0].name;
            imagesHolder.appendChild(text);
            text.addEventListener("click", () => {
                imagesHolder.removeChild(text);
                imagesHolder.removeChild(inputFile);
            })
        })

    })


})