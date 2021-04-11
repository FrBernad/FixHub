window.addEventListener("load", () => {
    let searchForm = document.getElementById("searchForm");

    let formSearchInput = document.getElementById("searchInput");
    let formFilterInput = document.getElementById("filterInput");
    let formOrderInput = document.getElementById("orderInput");

    let formSearchBar = document.getElementById("searchBar");

    let searchButton = document.getElementById("searchButton");
    let filterButtons = document.getElementsByClassName("filterButton");
    let emptyFilterButton = document.getElementById("emptyFilterButton");
    let orderButtons = document.getElementsByClassName("orderButton");

    formSearchBar.addEventListener("keypress", (e) => {
        if (e.key === "Enter") {
            const query = formSearchBar.value;
            formSearchInput.setAttribute("value", query);
            searchForm.submit();
        }
    });

    searchButton.addEventListener("click", () => {
        const query = formSearchBar.value;
        formSearchInput.setAttribute("value", query);
        if(formSearchInput.value.length > 50) {
            document.getElementById("searchInvalidLength").style.display = "";
        } else {
            searchForm.submit();
        }
    })

    for (const filterButton of filterButtons) {
        filterButton.addEventListener("click", () => {
            formFilterInput.setAttribute("value", filterButton.dataset.filter)
            searchForm.submit();
        })
    }

    emptyFilterButton.addEventListener("click", () => {
        formFilterInput.setAttribute("value", emptyFilterButton.dataset.filter)
        searchForm.submit();
    })

    for (const orderButton of orderButtons) {
        orderButton.addEventListener("click", () => {
            formOrderInput.setAttribute("value", orderButton.dataset.order)
            searchForm.submit();
        })
    }

})


