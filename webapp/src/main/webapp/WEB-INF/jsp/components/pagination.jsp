<div class="col-12 align-items-center justify-content-center mt-4
    ${results.totalPages<=1 ? 'd-none':''} ">
    <nav class="d-flex align-items-center justify-content-center">
        <ul class="pagination mb-0" id="pagination"
            data-searched="${searched}"
            data-page="${results.page}">
            <li class="page-item ${results.isFirst() ? 'disabled' : ''}">
                <a class="page-link" id="prev"
                   aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
            <c:forEach begin="1"
                       end="${results.totalPages}"
                       varStatus="status">
                <li class="page-item ${results.page == status.index-1 ? 'active' : ''}">
                    <a
                            class="page-link index"
                            data-index="${status.index}">${status.index}
                    </a>
                </li>
            </c:forEach>
            <li class="page-item ${results.isLast() ? 'disabled' : ''}">
                <a class="page-link" id="next"
                   aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
    </nav>
</div>