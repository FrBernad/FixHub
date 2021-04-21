<div class="accordion" id="accordion${status.index}">
    <div class="card accordionCard">
        <div class="card-header px-0">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-7">
                        <a href="<c:url value="/jobs/${contact.jobId}"/>">
                            <c:out value="${contact.jobProvided}"/>
                        </a>
                    </div>
                    <div class="col-4 d-flex align-items-center justify-content-end">
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
                            <span class="client-label"><spring:message code="dashboard.provider"/>:</span>
                            <a href="<c:url value="/user/${contact.user.userId}"/>">
                                <c:out value="${contact.user.name} ${contact.user.surname}"/>
                            </a>
                        </div>
                        <div class="col-12">
                            <p class="mb-0">
                                <span class="client-label"><spring:message code="dashboard.clientMessage"/>:</span>
                                <c:out value="${contact.message}"/>
                            </p>
                        </div>
                        <div class="col-12">
                            <p class="mb-0">
                                <span class="client-label"><spring:message code="dashboard.clientAddress"/>:</span>
                                <c:out value="${contact.contactInfo.state}, ${contact.contactInfo.city}, ${contact.contactInfo.street} ${contact.contactInfo.addressNumber}
                                ${contact.contactInfo.departmentNumber}
                                ${contact.contactInfo.floor} "/>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>