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
    let paginationForm = document.getElementById("searchForm");
    let formPageInput = document.getElementById("pageInput");

    let prevBtn = document.getElementById("prev");
    let nextBtn = document.getElementById("next");
    let currentPage = pagination.dataset.page;

    prevBtn.addEventListener("click", () => {
        formPageInput.setAttribute("value", (parseInt(currentPage) - 1).toString());
        paginationForm.submit();
    });

    nextBtn.addEventListener("click", () => {
        formPageInput.setAttribute("value", (parseInt(currentPage) + 1).toString());
        paginationForm.submit();
    });

    for (const index of indexs) {
        index.addEventListener("click", () => {
            const pageNum = index.dataset.index - 1;
            formPageInput.setAttribute("value", pageNum.toString());
            paginationForm.submit();
        })
    }
}


