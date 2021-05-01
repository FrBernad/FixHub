document.addEventListener("DOMContentLoaded", () => {
    let searchForm = document.getElementById("searchForm");

    let formPageInput = document.getElementById("pageInput");

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
})