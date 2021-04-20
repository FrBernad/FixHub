<div class="accordion" id="accordion${status.index}">
    <div class="card accordionCard">
        <div class="card-header px-0">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-6">
                        <a href="<c:url value="/jobs/${contact.jobId}"/>">
                            <c:out value="${contact.jobProvided}"/>
                        </a>
                    </div>
                    <div class="col-5 d-flex align-items-center justify-content-end">
                        <span>
                            <c:out value="${contact.date.toLocalDate()}"/>
                        </span>
                    </div>
                    <div class="col-1 d-flex align-items-center justify-content-center">
                        <i type="button" data-toggle="collapse"
                           data-target="#collapse${status.index}"
                           class="fas fa-chevron-down navbarText dropDownArrow"></i>
                    </div>
                </div>
            </div>
        </div>

        <div id="collapse${status.index}" class="collapse" data-parent="#accordion${status.index}">
            <div class="card-body px-0">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-12">
                            <a href="<c:url value="/jobs/${contact.jobId}"/>">
                                <c:out value="${contact.user.name} ${contact.user.surname}"/>
                            </a>
                        </div>
                        <div class="col-12">
                            <p class="mb-0">
                                <c:out value="${contact.message}"/>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>