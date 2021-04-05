let searchForm = document.getElementById("searchForm");
let searchFormInput = document.getElementById("searchFormInput");
document.getElementById("searchFormButton").addEventListener("click", () => {
    if (searchFormInput.value != null && searchFormInput.value !== "") {
        searchForm.submit()
    }
})
