window.addEventListener("load", () => {

    let jobForm = document.getElementById("jobForm")
    let imagesHolder = document.getElementById("imagesHolder");
    let processing = false;
    let jobFormButton = document.getElementById("jobFormButton");
    let addFileButtom = document.getElementById("addFileButtom");
    let inputFile = document.getElementById("inputFiles");
    let files = [];

    function FileListItems(files) {
        const b = new ClipboardEvent("").clipboardData || new DataTransfer();
        for (let i = 0, len = files.length; i < len; i++)
            b.items.add(files[i])
        return b.files
    }

    function inputFileUpdate() {
        files.push(inputFile.files[0]);
        const text = document.createElement("h2");
        text.textContent = inputFile.files[0].name + "aaaa";
        imagesHolder.appendChild(text);
        text.addEventListener("click", () => {
             imagesHolder.removeChild(text);
             let index = files.indexOf(inputFile);
             files.splice(index);
        })
    }

    inputFile.addEventListener("change", inputFileUpdate);

    addFileButtom.addEventListener("click", () => {
        inputFile.click();
    })

    jobFormButton.addEventListener("click", () => {
        if (processing) {
            return;
        }
        processing = true;
        jobFormButton.disabled = true;
        inputFile.removeEventListener("change", inputFileUpdate);
        inputFile.files = new FileListItems(files);

        jobForm.submit();
        processing = false;
    })


})