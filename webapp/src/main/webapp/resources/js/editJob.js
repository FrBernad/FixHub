document.addEventListener("DOMContentLoaded", () => {
    let editFormButton = document.getElementById("editFormButton");
    let pauseInput = document.getElementById("paused");
    let pauseCheck = document.getElementById("pauseCheck");
    let editJobForm = document.getElementById("editJobForm");
    let processing = false;
    let imagesQuantityText = document.getElementById("imagesQuantity");
    let imagesQuantity = imagesQuantityText.dataset.quantity;
    let imagesMax = imagesQuantityText.dataset.max;

    let imagesIdDeletedContainer = document.getElementById("imageIdDeletedContainer");

    let inputFile = document.getElementById("inputFiles");
    let addFileButton = document.getElementById("addFileButton");
    let imagesHolder = document.getElementById("imagesHolder");
    let files = [];

    let imageDelete = document.getElementsByClassName("imageDelete");

    let loadingSpinner = document.getElementById("loadingSpinner");


    if ((document.getElementById("carousel") !== null)) {
        document.getElementById("carousel").getElementsByTagName("div")[0].className += " active";
    }

    if (pauseCheck.value === "true") {
        pauseCheck.setAttribute("checked", "");
    } else {
        pauseCheck.removeAttribute("checked");
    }

    if (document.getElementById("carousel") !== null) {

        let carousel = document.getElementById("carousel");
        let jobImages = document.getElementsByClassName("jobImages");
        for (const image of imageDelete) {
            let imageContainer = document.getElementById(image.dataset.imageId);

            image.addEventListener("click", () => {
                if (parseInt(imagesQuantity) === parseInt(imagesMax)) {
                    addFileButton.disabled = false;
                    addFileButton.classList.replace('buttonDisabled','buttonEnabled');
                }
                carousel.removeChild(imageContainer);
                let aux = document.createElement("input");
                aux.type = "hidden";
                aux.name = "imagesIdDeleted";
                aux.value = image.dataset.imageId;
                imagesIdDeletedContainer.appendChild(aux);
                imagesQuantity--;
                imagesQuantityText.textContent = imagesQuantity.toString();
                if (jobImages.length !== 0) {
                    carousel.getElementsByTagName("div")[0].className += " active";
                }
            })


        }
    }

    function fileListItems(files) {
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
        imagesQuantity++;
        imagesQuantityText.textContent = imagesQuantity.toString();
        const icon = document.createElement("i");
        icon.className += "fas fa-times ml-1";
        file.appendChild(icon);
        imagesHolder.appendChild(file);
        if (parseInt(imagesQuantity) === parseInt(imagesMax)) {
            addFileButton.disabled = true;
            addFileButton.classList.replace('buttonEnabled','buttonDisabled');

        }

        file.addEventListener("click", () => {
            if (parseInt(imagesQuantity) === parseInt(imagesMax)) {
                addFileButton.disabled = false;
                addFileButton.classList.replace('buttonDisabled','buttonEnabled');
            }
            imagesHolder.removeChild(file);
            let index = files.indexOf(inputFile);
            files.splice(index);
            imagesQuantity--;
            imagesQuantityText.textContent = imagesQuantity.toString();
        })
    }


    inputFile.addEventListener("change", inputFileUpdate);


    addFileButton.addEventListener("click", clickInputFile);

    if(parseInt(imagesQuantity) === parseInt(imagesMax) ){
        addFileButton.className+= " buttonDisabled ";
        addFileButton.disabled=true;
    }else{
        addFileButton.className+= " buttonEnabled ";
    }

    function clickInputFile() {
        inputFile.click();
    }

    editFormButton.addEventListener("click", () => {
        if (processing) {
            return;
        }

        pauseInput.value = pauseCheck.checked;

        processing = true;
        editFormButton.disabled = true;
        loadingSpinner.removeAttribute("hidden");
        inputFile.removeEventListener("change", inputFileUpdate);
        inputFile.files = new fileListItems(files);

        console.log(imagesIdDeletedContainer);
        editJobForm.submit();
    })
});