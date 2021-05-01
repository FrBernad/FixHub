document.addEventListener("DOMContentLoaded", () => {

    let jobForm = document.getElementById("jobForm")
    let imagesHolder = document.getElementById("imagesHolder");
    let processing = false;
    let jobFormButton = document.getElementById("jobFormButton");
    let addFileButton = document.getElementById("addFileButton");
    let inputFile = document.getElementById("inputFiles");
    let files = [];

    let categories = document.getElementsByClassName("categoryButton");
    let categoryName = document.getElementById("categoryName");
    let categoryInput = document.getElementById("jobCategory");

    let loadingSpinner = document.getElementById("loadingSpinner");

    for (const category of categories) {
        category.addEventListener("click", () => {
            const name = category.dataset.name;
            categoryName.innerText =  category.dataset.i18name;
            categoryInput.value = name;
        })
    }

    function FileListItems(files) {
        const b = new ClipboardEvent("").clipboardData || new DataTransfer();
        for (let i = 0, len = files.length; i < len; i++)
            b.items.add(files[i])
        return b.files
    }

    function inputFileUpdate() {
        files.push(inputFile.files[0]);
        const file = document.createElement("button");
        file.type = "button";
        file.className += "imgFile m-2";
        file.textContent = inputFile.files[0].name;
        const icon = document.createElement("i");
        icon.className += "fas fa-times ml-1";
        file.appendChild(icon);
        imagesHolder.appendChild(file);

        file.addEventListener("click", () => {
            imagesHolder.removeChild(file);
            let index = files.indexOf(inputFile);
            files.splice(index);
        })
    }


    inputFile.addEventListener("change", inputFileUpdate);

    addFileButton.addEventListener("click", () => {
        inputFile.click();
    })


    jobFormButton.addEventListener("click", () => {
        if (processing) {
            return;
        }
        processing = true;
        jobFormButton.disabled = true;
        loadingSpinner.removeAttribute("hidden");
        inputFile.removeEventListener("change", inputFileUpdate);
        inputFile.files = new FileListItems(files);
        jobForm.submit();
        processing = false;
    })


})