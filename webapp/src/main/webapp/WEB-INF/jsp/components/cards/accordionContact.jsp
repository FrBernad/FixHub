<div class="accordion" id="accordion${status.index}">
    <div class="card accordionCard">
        <div class="card-header px-0">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-11">
                        <a href="<c:url value="/jobs/${contact.job.id}"/>"><c:out value="${contact.job.jobProvided}"/></a> |
                        <a class="names" href="<c:url value="/user/${contact.provider.id}"/>"><c:out
                                value="${contact.provider.name} ${contact.provider.surname}"/></a>
                    </div>
                    <div class="col-1 d-flex align-items-center justify-content-end">
                        <i type="button" data-toggle="collapse"
                           data-target="#collapse${status.index}"
                           class="fas fa-chevron-down navbarText dropDownArrow"></i>
                    </div>
                    <div class="col-12 d-flex align-items-center justify-content-start">
                        <span>
                            <c:out value="${contact.date}"/>
                        </span>
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
                            <a class="names" href="<c:url value="/user/${contact.provider.id}"/>">
                                <c:out value="${contact.provider.name} ${contact.provider.surname}"/>
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
                        <div class="col-12">
                            <p class="mb-0">
                                <span class="client-label"><spring:message code="clientContactCard.status"/>:</span>
                                <spring:message code="contact.status.${contact.status}"/>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>