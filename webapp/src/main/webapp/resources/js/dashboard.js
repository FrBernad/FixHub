let panelTitle = $('#panelTitle');
let dashboard = $('#tabDashboard');
let work = $('#tabWorks');
let contact = $('#tabContacts');

dashboard.click(function (e) {
    e.preventDefault();
    $(this).tab('show')
    panelTitle.html(dashboard.data('name'));
});

work.click(function (e) {
    e.preventDefault();
    $(this).tab('show')
    panelTitle.html(work.data('name'));
});

contact.click(function (e) {
    e.preventDefault();
    $(this).tab('show')
    panelTitle.html(contact.data('name'));
});

let pagination = document.getElementById("pagination");
if (pagination !== null) {

    let indexs = document.getElementsByClassName("index");
    let searchForm = document.getElementById("searchForm");
    let formPageInput = document.getElementById("pageInput");
    let orderButtons = document.getElementsByClassName("orderButton");
    let formSearchInput = document.getElementById("searchInput");
    let formOrderInput = document.getElementById("orderInput");
    let formSearchBar = document.getElementById("searchBar");


    let prevBtn = document.getElementById("prev");
    let nextBtn = document.getElementById("next");
    let currentPage = pagination.dataset.page;

    if (pagination.dataset.searched === "true") {
        work.tab("show")
        panelTitle.html(contact.data('name'));
    }

    for (const orderButton of orderButtons) {
        orderButton.addEventListener("click", () => {
            formOrderInput.setAttribute("value", orderButton.dataset.order)
            searchForm.submit();
        })
    }

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
        if (formSearchInput.value.length > 50) {
            document.getElementById("searchInvalidLength").style.display = "";
        } else {
            searchForm.submit();
        }
    })

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
}


