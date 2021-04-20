window.addEventListener("load", () => {
    let pagination = document.getElementById("pagination");
    if (pagination !== null) {

        let indexs = document.getElementsByClassName("index");
        let searchForm = document.getElementById("searchForm");
        let formPageInput = document.getElementById("pageInput");

        let prevBtn = document.getElementById("prev");
        let nextBtn = document.getElementById("next");
        let currentPage = pagination.dataset.page;


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
})
