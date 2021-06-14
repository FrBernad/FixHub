<div class="accordion" id="accordion${status.index}">
    <div class="card accordionCard">
        <div class="card-header px-0" data-toggle="collapse" data-target="#collapse${status.index}" style="cursor: pointer">
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
                            <c:choose>

                                <c:when test="${contact.isWorkPending()}">
                                    <spring:message code="clientContactCard.pendingJob"/>
                                </c:when>
                                <c:when test="${contact.isWorkInProgress()}">
                                    <spring:message code="clientContactCard.workInProgress"/>
                                </c:when>
                                <c:when test="${contact.isWorkDone()}">
                                    <spring:message code="clientContactCard.completedJob"/>
                                </c:when>
                                <c:otherwise>
                                    <spring:message code="clientContactCard.rejectedJob"/>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <div class="col-12 p-2">
                            <c:choose>

                                <c:when test="${contact.isWorkPending()}">
                                    <div class="container p-2"
                                         style="background-color: aliceblue;border-radius: 8px;">
                                        <div class="cols-12">

                                            <div class="row px-2">
                                                <p class="m-2"><b><spring:message
                                                        code="clientContactCard.jobDecision"/></b>
                                                </p>
                                            </div>
                                            <div class="row justify-content-end align-content-end px-2">
                                                <div class="cols-6 m-1">
                                                    <form id="acceptJobForm${contact.id}"
                                                          action="<c:url value='/user/dashboard/contacts/acceptJob'/>"
                                                          method="POST">
                                                        <input type="hidden" name="contactId" value="${contact.id}">
                                                    </form>
                                                    <button class="acceptJobBtn" type="submit" form="acceptJobForm${contact.id}" id="acceptJobBtn${contact.id}">
                                                        <spring:message code="clientContactCard.acceptJobBtn"/>
                                                    </button>
                                                </div>
                                                <div class="cols-6 m-1">
                                                    <form id="rejectJobForm${contact.id}"
                                                          action="<c:url value='/user/dashboard/contacts/rejectJob'/>"
                                                          method="POST">
                                                        <input type="hidden" name="contactId" value="${contact.id}">
                                                    </form>
                                                    <button class="rejectJobBtn"  type="submit" form="rejectJobForm${contact.id}" id="rejectJobBtn${contact.id}">
                                                        <spring:message code="clientContactCard.rejectJobBtn"/>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:when>
                                <c:when test="${contact.isWorkInProgress()}">
                                    <div class="container p-2"
                                         style="background-color: aliceblue;border-radius: 8px;">
                                        <div class="cols-12">
                                            <div class="row px-2">
                                                <p class="ml-2 mb-1"><b>
                                                    <spring:message
                                                            code="clientContactCard.jobCompletedIntro"/> </b></p>
                                            </div>
                                            <div class="row px-2">
                                                <p class="ml-2 mb-2" style="font-size: small; color: #666666">
                                                    <spring:message code="clientContactCard.jobCompletedIntroInfo"/></p>
                                            </div>
                                            <div class="row justify-content-end align-content-end px-2">
                                                <form id="completedJobForm${contact.id}"
                                                      action="<c:url value='/user/dashboard/contacts/completedJob'/>"
                                                      method="POST">
                                                    <input type="hidden" name="contactId" value="${contact.id}">
                                                </form>
                                                <button class="completedJobBtn m-1" type="submit" form="completedJobForm${contact.id}" id="completedJobBtn${contact.id}">
                                                    <spring:message code="clientContactCard.completedJobBtn"/>
                                                </button>
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
</div>
