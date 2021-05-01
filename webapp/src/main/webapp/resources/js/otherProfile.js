document.addEventListener("DOMContentLoaded", () => {

    let followBtn = document.getElementById("followBtn");
    let unfollowBtn = document.getElementById("unfollowBtn");
    let followForm = document.getElementById("followForm");
    let unfollowForm = document.getElementById("unfollowForm");

    let processing = false;
    if (followForm !== null) {
        followBtn.addEventListener("click", () => {
            if (processing) {
                return;
            }
            processing = true;
            followBtn.disabled = true;
            followForm.submit();
            processing = false;
        })
    }

    if (unfollowForm !== null) {
        unfollowBtn.addEventListener("click", () => {
            if (processing) {
                return;
            }
            processing = true;
            unfollowBtn.disabled = true;
            unfollowForm.submit();
            processing = false;
        })
    }

})
