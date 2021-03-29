<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="card reviewCard">
    <div class="card-body container-fluid justify-items-center align-items-center">
        <div class="row align-items-start">
            <div class="col">
                <div class="container-fluid">
                    <div class="row align-items-center">
                        <div class="col align-items-lg-start align-items-center">
                            <h5 class="card-title">${review.rating}</h5>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col d-flex justify-content-end ">
                <p style="color: #6699CC">${review.creationDate}</p>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <div class="card-text">${review.description}</div>
            </div>
        </div>

    </div>
</div>