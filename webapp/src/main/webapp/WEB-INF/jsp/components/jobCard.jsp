<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="col-12 col-md-4 mb-4 mb-md-0 d-flex align-items-center justify-content-center">
    <div class="card jobCard">
        <div style="height: 100%; width: 100%">
            <img
                    class="card-img serviceImg"
                    src="https://lh3.googleusercontent.com/proxy/9WM2qD8iTUjyFmhfFkv5_8M3ivw-5Rx5eA-LmjmAgH5pcdphIHlePkKhEresPmkKWd-K8g89wWHgMRLtJJ5IAtDDEMV0-SCFr05oHLGa85luYR9Pfes6JGV-NhlWGXEsHttHDUyhDmbxMw"
                    alt="User Profile Img">
        </div>
        <div style="padding: 1.25rem">
            <div class="row justify-content-center">
                <div class="col-md-12">
                    <h5 class="card-title"><c:out value="${job.description}"/></h5>
                    <p class="card-text"><c:out value="${job.provider.name}"/></p>
                </div>
            </div>
        </div>
        <a href="<c:url value='/jobs/${job.id}'/>" class="stretched-link"></a>
    </div>
</div>