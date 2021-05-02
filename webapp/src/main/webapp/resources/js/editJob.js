document.addEventListener("DOMContentLoaded", () => {
    let editFormButton = document.getElementById("editFormButton");
    let pauseInput = document.getElementById("paused");
    let pauseCheck = document.getElementById("pauseCheck");
    let editJobForm = document.getElementById("editJobForm");
    let processing = false;

    let imagesIdDeleted = [];
    let imagesIdDeletedContainer = document.getElementById("imageIdDeletedContainer");

    let inputFile = document.getElementById("inputFiles");
    let addFileButton = document.getElementById("addFileButton");
    let imagesHolder = document.getElementById("imagesHolder");
    let files = [];

    let imageDelete = document.getElementsByClassName("imageDelete");

    if ((document.getElementById("carousel") !== null)) {
        document.getElementById("carousel").getElementsByTagName("div")[0].className += " active";
    }

    if(pauseCheck.value === "true") {
        pauseCheck.setAttribute("checked", "");
    } else {
        pauseCheck.removeAttribute("checked");
    }

    if (document.getElementById("carousel") !== null) {

        let carousel = document.getElementById("carousel");
        let jobImages = document.getElementsByClassName("jobImages");
        for (const image of imageDelete) {
            let imageContainer = document.getElementById(image.dataset.imageId);

            image.addEventListener("click",()=>{
                carousel.removeChild(imageContainer);
                let aux = document.createElement("input");
                aux.type = "hidden";
                aux.name="imagesIdDeleted";
                aux.value=image.dataset.imageId;
                imagesIdDeletedContainer.appendChild(aux);
                if(jobImages.length !== 0){
                    carousel.getElementsByTagName("div")[0].className+=" active";
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
        file.className += "imgFile m-2 d-flex justify-content-center align-items-center";
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

    editFormButton.addEventListener("click", () => {
        if (processing) {
            return;
        }

        pauseInput.value = pauseCheck.checked;

        processing = true;
        editFormButton.disabled = true;

        inputFile.removeEventListener("change", inputFileUpdate);
        inputFile.files = new fileListItems(files);

        console.log(imagesIdDeletedContainer);
        editJobForm.submit();
    })
});