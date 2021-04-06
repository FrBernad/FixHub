let searchForm = document.getElementById("searchForm");
let searchFormInput = document.getElementById("searchFormInput");

searchFormInput.addEventListener("keypress", (e) => {
    if (e.key === "Enter")
        searchFormInput.submit();
});

document.getElementById("searchFormButton").addEventListener("click", () => {
    searchForm.submit()
});
