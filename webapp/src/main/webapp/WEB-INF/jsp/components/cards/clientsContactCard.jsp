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
                            <c:choose>

                                <c:when test="${contact.isWorkPending()}">
                                    <div class="row">
                                        <div class="cols-6">
                                            <form id="acceptJobForm"
                                                  action="<c:url value='/user/dashboard/contacts/acceptJob'/>"
                                                  method="POST">
                                                <input type="hidden" name="contactId" value="${contact.id}">
                                            </form>
                                            <button style="color:green" id="acceptJobBtn">
                                                <spring:message code="clientContactCard.acceptJobBtn"/>
                                            </button>
                                        </div>
                                        <div class="cols-6">
                                            <form id="rejectJobForm"
                                                  action="<c:url value='/user/dashboard/contacts/rejectJob'/>"
                                                  method="POST">
                                                <input type="hidden" name="contactId" value="${contact.id}">
                                            </form>
                                            <button style="color: red" id="rejectJobBtn">
                                               <spring:message code="clientContactCard.rejectJobBtn"/>
                                            </button>
                                        </div>
                                    </div>
                                </c:when>
                                <c:when test="${contact.isWorkInProgress()}">
                                    <form id="completedJobForm"
                                          action="<c:url value='/user/dashboard/contacts/completedJob'/>" method="POST">
                                        <input type="hidden" name="contactId" value="${contact.id}">
                                    </form>
                                    <button style="color: cornflowerblue" id="completedJobBtn">
                                        <spring:message code="clientContactCard.completedJobBtn"/>
                                    </button>
                                </c:when>
                                <c:when test="${contact.isWorkDone()}">
                                     <span class="client-label"><spring:message code="clientContactCard.state"/>:</span>
                                        <spring:message code="clientContactCard.completedJob"/>
                                </c:when>
                                <c:otherwise>
                                    <span class="client-label"><spring:message code="clientContactCard.state"/>:</span>
                                        <spring:message code="clientContactCard.completedJob"/>
                                </c:otherwise>
                            </c:choose>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src='<c:url value="/resources/js/contactCard.js"/>'></script>
