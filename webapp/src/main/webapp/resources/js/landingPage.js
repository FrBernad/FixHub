document.addEventListener("DOMContentLoaded", () => {
    let searchForm = document.getElementById("searchForm");
    let searchInput = document.getElementById("searchInput");
    document.getElementById("categoryInput").setAttribute("value","");
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
        if(searchFormInput.value.length > 50) {
            document.getElementById("searchInvalidLength").style.display = "";
        } else {
            searchForm.submit();
        }
    });
    



})


