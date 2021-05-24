document.addEventListener("DOMContentLoaded", () => {
    let inputState = document.getElementById("state");
    let states = document.getElementsByClassName("stateButton");
    let stateName = document.getElementById("stateName");
    let startTime = document.getElementById("startTime");
    let endTime = document.getElementById("endTime");

    let startTimeStr = startTime.getAttribute("value").substring(0, 2);
    let endTimeStr = endTime.getAttribute("value").substring(0, 2);

    for (const state of states) {
        state.addEventListener("click", () => {
            inputState.value = state.dataset.id;
            stateName.textContent = state.dataset.name;
        })
    }

    $('.startTimepicker').timepicker({
        timeFormat: 'HH:mm',
        interval: 60,
        minTime: '0',
        maxTime: '23:00',
        defaultTime: startTimeStr,
        startTime: '0',
        dynamic: false,
        dropdown: true,
        scrollbar: true
    });

    $('.endTimepicker').timepicker({
        timeFormat: 'HH:mm',
        interval: 60,
        minTime: '0',
        maxTime: '23:00',
        defaultTime: endTimeStr,
        startTime: '0',
        dynamic: false,
        dropdown: true,
        scrollbar: true
    });

})
