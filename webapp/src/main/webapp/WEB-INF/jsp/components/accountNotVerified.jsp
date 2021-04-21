<c:if test="${loggedUser.hasRole('NOT_VERIFIED')}">
    <div class="modal fade" tabindex="-1" data-show="true" id="notVerifiedModal">
        <div class="modal-dialog  modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-12">
                                <h1 class="text-center" style="font-size: 18px;font-weight: bold">
                                        <spring:message code="errors.notVerified.title"/>
                            </div>
                            <div class="col-12 mt-2">
                                <h2 class="text-center" style="font-size: 18px">
                                    <spring:message code="errors.notVerified.notRecieved"/>
                                    <span id="resendBtn">
                                        <spring:message code="errors.notVerified.resend"/>
                                    </span>
                                </h2>
                                <form action="<c:url value='/user/verifyAccount/resend'/>"
                                      method="POST" id="resendForm"
                                      type="hidden"></form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </div>
    <script src='<c:url value="/resources/js/notVerifiedModal.js"/>'></script>
</c:if>
