window.addEventListener("load", () => {
    let searchForm = document.getElementById("searchForm");
    let searchInput = document.getElementById("searchInput");
    document.getElementById("filterInput").setAttribute("value","");
    document.getElementById("orderInput").setAttribute("value","");

    let searchFormInput = document.getElementById("searchFormInput");
    let searchFormButton = document.getElementById("searchFormButton");

    searchFormInput.addEventListener("keypress", (e) => {
        if (e.key === "Enter") {
            searchInput.setAttribute("value", searchFormInput.value)
            searchForm.submit();
        }
    });

    searchFormButton.addEventListener("click", () => {
        searchInput.setAttribute("value", searchFormInput.value)
        searchForm.submit();
    });

})


