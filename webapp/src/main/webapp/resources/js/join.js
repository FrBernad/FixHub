document.addEventListener("DOMContentLoaded", () => {
    let inputState = document.getElementById("state");
    let states = document.getElementsByClassName("stateButton");
    let stateName = document.getElementById("stateName");


    for (const state of states) {
        state.addEventListener("click", () => {
            inputState.value = state.dataset.id;
            stateName.textContent = state.dataset.name;
        })
    }

    $('.timepicker').timepicker({
        timeFormat: 'HH:mm',
        interval: 60,
        minTime: '0',
        maxTime: '23:00',
        defaultTime: '11',
        startTime: '0',
        dynamic: false,
        dropdown: true,
        scrollbar: true
    });

})
