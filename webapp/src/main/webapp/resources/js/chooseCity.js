document.addEventListener("DOMContentLoaded", () => {
    let cities = document.getElementsByClassName("cityButton");
    let cityName = document.getElementById("cityName");
    let citiesHolder = document.getElementById("citiesHolder");

    let chooseCityForm = document.getElementById("chooseCityForm");
    let submitButton = document.getElementById("submitButton");

    let citiesArray = [];

    for (const city of cities) {
        city.addEventListener("click", () => {
            const id = city.dataset.id;
            const name = city.dataset.name;
            if(!citiesArray.includes(id)) {
                cityName.innerText = name;
                citiesArray.push(id);
                const cityTag = document.createElement("button");
                cityTag.type="button";
                cityTag.className+=" cityTag m-2";
                cityTag.textContent = name;
                const icon = document.createElement("i");
                icon.className += "fas fa-times ml-1";
                cityTag.appendChild(icon);
                citiesHolder.appendChild(cityTag);

                cityTag.addEventListener("click", () => {
                    citiesHolder.removeChild(cityTag);
                    let index = citiesArray.indexOf(id);
                    citiesArray.splice(index);
                    cityName.innerText = "";
                })
            }
        })
    }

    function citiesSubmit() {
        for(const city of citiesArray) {
            const input = document.createElement("input");
            input.type = "hidden";
            input.name = "city";
            input.value = city;
            chooseCityForm.appendChild(input);
        }
    }

    submitButton.addEventListener("click", () => {
        citiesSubmit();
        chooseCityForm.submit();
    })


})