<div class="accordion" id="accordion${status.index}">
    <div class="card accordionCard">
        <div class="card-header px-0" data-toggle="collapse" data-target="#collapse${status.index}"
             style="cursor: pointer">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-8">
                        <a class="preventParent" href="<c:url value="/jobs/${contact.job.id}"/>">
                            <c:out value="${contact.job.jobProvided}"/>
                        </a>
                        |
                        <a class="preventParent names" href="<c:url value="/user/${contact.user.id}"/>">
                            <c:out value="${contact.user.name} ${contact.user.surname}"/>
                        </a>
                    </div>
                    <div class="col-3 d-flex align-items-center justify-content-end">
                        <span>
                            <c:out value="${contact.date}"/>
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
                            <span class="client-label"><spring:message code="dashboard.client"/>:</span>
                            <a class="preventParent names" href="<c:url value="/user/${contact.user.id}"/>">
                                <c:out value="${contact.user.name} ${contact.user.surname}"/>
                            </a>
                        </div>
                        <div class="col-12">
                            <span class="client-label"><spring:message code="dashboard.clientPhoneNumber"/>:</span>
                            <c:out value="${contact.user.phoneNumber}"/>
                        </div>
                        <div class="col-12">
                            <span class="client-label"><spring:message code="dashboard.clientEmail"/>:</span>
                            <c:out value="${contact.user.email}"/>
                        </div>
                        <div class="col-12">
                            <span class="client-label"><spring:message code="dashboard.clientAddress"/>:</span>
                            <c:out value="${contact.contactInfo.city}, ${contact.contactInfo.state},
                            ${contact.contactInfo.street} ${contact.contactInfo.addressNumber}
                            ${contact.contactInfo.floor} ${contact.contactInfo.departmentNumber}
                            "/>
                        </div>

                        <div class="col-12">
                            <p class="mb-0">
                                <span class="client-label"><spring:message code="dashboard.clientMessage"/>:</span>
                                <c:out value="${contact.message}"/>
                            </p>
                        </div>
                        <div class="col-12">
                            <span class="client-label"><spring:message code="clientContactCard.status"/>:</span>
                            <spring:message code="contact.status.${contact.status}"/>
                        </div>

                        <c:choose>
                            <c:when test="${contact.isWorkPending()}">
                                <div class="col-12 p-2">
                                    <div class="container p-2"
                                         style="background-color: aliceblue;border-radius: 8px;">
                                        <div class="row">
                                            <div class="col-12">
                                                <div class="row">
                                                    <div class="col-12 col-md-6 pl-4 my-2 d-flex align-items-center justify-content-center justify-content-md-start">
                                                        <p class="m-2 font-weight-bold">
                                                            <spring:message code="clientContactCard.jobDecision"/>
                                                        </p>
                                                    </div>
                                                    <div class="col-12 col-md-6 pr-4 my-2  d-flex align-items-center justify-content-center justify-content-md-end">
                                                        <form id="acceptJobForm${contact.id}"
                                                              action="<c:url value='/user/dashboard/contacts/acceptJob'/>"
                                                              method="POST">
                                                            <input type="hidden" name="contactId" value="${contact.id}">
                                                        </form>
                                                        <button class="mr-4 finishBtn"
                                                                type="submit"
                                                                form="acceptJobForm${contact.id}"
                                                                id="acceptJobBtn${contact.id}">
                                                            <spring:message code="clientContactCard.acceptJobBtn"/>
                                                            <i class="fas fa-check ml-2"></i>
                                                        </button>
                                                        <form id="rejectJobForm${contact.id}"
                                                              action="<c:url value='/user/dashboard/contacts/rejectJob'/>"
                                                              method="POST">
                                                            <input type="hidden" name="contactId" value="${contact.id}">
                                                        </form>
                                                        <button class="closeBtn" type="submit"
                                                                form="rejectJobForm${contact.id}"
                                                                id="rejectJobBtn${contact.id}">
                                                            <spring:message code="clientContactCard.rejectJobBtn"/>
                                                            <i class="fas fa-times ml-2"></i>
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:when>
                            <c:when test="${contact.isWorkInProgress()}">
                                <div class="col-12 col-md-10 p-2">
                                    <div class="container p-2"
                                         style="background-color: aliceblue;border-radius: 8px;">
                                        <div class="row">
                                            <div class="col-12">
                                                <div class="row">
                                                    <div class="col-12 col-md-7 pl-4 my-2 d-flex align-items-center justify-content-center justify-content-md-start">
                                                        <p class="font-weight-bold mb-0">
                                                            <spring:message
                                                                    code="clientContactCard.jobCompletedIntro"/>
                                                        </p>
                                                    </div>
                                                    <div class="col-12 col-md-5 pr-4 d-flex my-2 d-flex align-items-center justify-content-center justify-content-md-end">
                                                        <form id="completedJobForm${contact.id}"
                                                              action="<c:url value='/user/dashboard/contacts/completedJob'/>"
                                                              method="POST">
                                                            <input type="hidden" name="contactId" value="${contact.id}">
                                                        </form>
                                                        <button class="finishBtn" type="submit"
                                                                form="completedJobForm${contact.id}"
                                                                id="completedJobBtn${contact.id}">
                                                            <spring:message code="clientContactCard.completedJobBtn"/>
                                                            <i class="fas fa-check ml-2"></i>
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:when>
                        </c:choose>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
