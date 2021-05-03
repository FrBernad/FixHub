document.addEventListener("DOMContentLoaded", () => {
    let searchForm = document.getElementById("searchForm");

    let formSearchInput = document.getElementById("searchInput");
    let formCategoryInput = document.getElementById("categoryInput");
    let formOrderInput = document.getElementById("orderInput");
    let formPageInput = document.getElementById("pageInput");
    let formStateInput = document.getElementById("stateInput");
    let formCityInput = document.getElementById("cityInput");

    let formSearchBar = document.getElementById("searchBar");

    let searchButton = document.getElementById("searchButton");
    let categoryButtons = document.getElementsByClassName("categoryButton");
    let stateButtons = document.getElementsByClassName("stateButton");
    let cityButtons = document.getElementsByClassName("cityButton");
    let emptyCategoryButton = document.getElementById("emptyCategoryButton");
    let emptyStateButton = document.getElementById("emptyStateButton");
    let emptyCityButton = document.getElementById("emptyCityButton");
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
            formSearchInput.setAttribute("value", "");
            document.getElementById("searchInvalidLength").style.display = "";
        } else {
            formPageInput.setAttribute("value", "0");
            searchForm.submit();
        }
    })

    for (const categoryBtn of categoryButtons) {
        categoryBtn.addEventListener("click", () => {
            formCategoryInput.setAttribute("value", categoryBtn.dataset.category)
            formPageInput.setAttribute("value", "0");
            searchForm.submit();
        })
    }

    emptyCategoryButton.addEventListener("click", () => {
        formCategoryInput.setAttribute("value", emptyCategoryButton.dataset.category)
        formPageInput.setAttribute("value", "0");
        searchForm.submit();
    })

    for (const stateBtn of stateButtons) {
        stateBtn.addEventListener("click", () => {
            formPageInput.setAttribute("value", "0");
            formStateInput.setAttribute("value", stateBtn.dataset.state)
            searchForm.submit();
        })
    }

    emptyStateButton.addEventListener("click", () => {
        formPageInput.setAttribute("value", "0");
        formStateInput.setAttribute("value", emptyStateButton.dataset.state)
        searchForm.submit();
    })

    for (const cityBtn of cityButtons) {
        cityBtn.addEventListener("click", () => {
            formPageInput.setAttribute("value", "0");
            formCityInput.setAttribute("value", cityBtn.dataset.city)
            searchForm.submit();
        })
    }

    emptyCityButton.addEventListener("click", () => {
        formPageInput.setAttribute("value", "0");
        formCityInput.setAttribute("value", emptyCityButton.dataset.city)
        searchForm.submit();
    })

    for (const orderButton of orderButtons) {
        orderButton.addEventListener("click", () => {
            formPageInput.setAttribute("value", "0");
            formOrderInput.setAttribute("value", orderButton.dataset.order)
            searchForm.submit();
        })
    }

})


