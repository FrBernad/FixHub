document.addEventListener("DOMContentLoaded", () => {
    let searchForm = document.getElementById("searchForm");

    let formSearchInput = document.getElementById("searchInput");
    let formFilterInput = document.getElementById("filterInput");
    let formOrderInput = document.getElementById("orderInput");
    let formPageInput = document.getElementById("pageInput");

    let formSearchBar = document.getElementById("searchBar");

    let searchButton = document.getElementById("searchButton");
    let filterButtons = document.getElementsByClassName("filterButton");
    let emptyFilterButton = document.getElementById("emptyFilterButton");
    let orderButtons = document.getElementsByClassName("orderButton");

    let indexs = document.getElementsByClassName("index");

    let prevBtn = document.getElementById("prev");
    let nextBtn = document.getElementById("next");
    let currentPage = document.getElementById("pagination").dataset.page;

    prevBtn.addEventListener("click", () => {
        formPageInput.setAttribute("value", (parseInt(currentPage) - 1).toString());
        searchForm.submit();
    });

    nextBtn.addEventListener("click", () => {
        formPageInput.setAttribute("value", (parseInt(currentPage) + 1).toString());
        searchForm.submit();
    });

    for (const index of indexs) {
        index.addEventListener("click", () => {
            const pageNum = index.dataset.index - 1;
            formPageInput.setAttribute("value", pageNum.toString());
            searchForm.submit();
        })
    }

    formSearchBar.addEventListener("keypress", (e) => {
        if (e.key === "Enter") {
            const query = formSearchBar.value;
            formSearchInput.setAttribute("value", query);
            formPageInput.setAttribute("value", "0");
            searchForm.submit();
        }
    });

    searchButton.addEventListener("click", () => {
        const query = formSearchBar.value;
        formSearchInput.setAttribute("value", query);
        if (formSearchInput.value.length > 50) {
            document.getElementById("searchInvalidLength").style.display = "";
        } else {
            formPageInput.setAttribute("value", "0");
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


