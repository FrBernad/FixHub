document.addEventListener("DOMContentLoaded", () => {
    let contactForm = document.getElementById("contactForm")
    let processing = false;
    let contactFormButton = document.getElementById("contactFormButton");
    let userContact = document.getElementsByClassName("userContact");
    let newUserContact = document.getElementById("newUserContact");
    let city = document.getElementById("city");
    let street = document.getElementById("street");
    let addressNumber = document.getElementById("addressNumber");
    let floor = document.getElementById("floor");
    let departmentNumber = document.getElementById("departmentNumber");
    let contactId = document.getElementById("contactInfoId");

    let cityOptions = document.getElementsByClassName("cityBtn");
    let cityError = document.getElementById("cityError");
    let cityNames = [];
    let cityToogle = document.getElementById("cityDropdownBtn");

    for (const cityBtn of cityOptions) {
        let cityname = cityBtn.dataset.name;
        cityNames.push(cityname);
        cityBtn.addEventListener("click", () => {
            cityError.classList.add("invisible");
            city.value = cityname;
        })
    }

    city.addEventListener("change", () => {
        if (!cityNames.includes(city.value) && city.value !== '')
            cityError.classList.remove("invisible");
        else
            cityError.classList.add("invisible");
    });

    contactFormButton.addEventListener("click", () => {
        if (processing) {
            return;
        }

        if (document.getElementById("dropdownMenuButton") === null)
            contactId.setAttribute("value", "-1");

        processing = true;
        contactFormButton.disabled = true;
        contactForm.submit();
        processing = false;
    })

    if (newUserContact !== null) {
        newUserContact.addEventListener("click", () => {

            city.textContent = '';
            city.setAttribute("value", '');
            city.value = '';
            city.readOnly = false;
            cityError.classList.add("invisible");

            street.setAttribute("value", '');
            street.value = '';
            street.readOnly = false;

            addressNumber.setAttribute("value", '');
            addressNumber.value = '';
            addressNumber.readOnly = false;

            floor.setAttribute("value", '');
            floor.value = '';
            floor.readOnly = false;

            departmentNumber.setAttribute("value", '');
            departmentNumber.value = '';
            departmentNumber.readOnly = false;

            contactId.setAttribute("value", "-1");

            cityToogle.removeAttribute("disabled");

        })
    }


    for (const contact of userContact) {
        contact.addEventListener("click", () => {
            let info = contact.dataset;

            city.value = info.city;
            city.setAttribute("value", info.city.toString());
            city.readOnly = true;

            if (!cityNames.includes(city.value) && city.value !== '')
                cityError.classList.remove("invisible");
            else
                cityError.classList.add("invisible");

            street.value = info.street;
            street.setAttribute("value", info.street.toString());
            street.readOnly = true;

            addressNumber.value = info.addressNumber;
            addressNumber.setAttribute("value", info.addressNumber.toString());
            addressNumber.readOnly = true;

            floor.value = info.floor;
            floor.setAttribute("value", info.floor.toString());
            floor.readOnly = true;

            departmentNumber.value = info.departmentNumber;
            departmentNumber.setAttribute("value", info.departmentNumber.toString());
            departmentNumber.readOnly = true;

            contactId.setAttribute("value", info.infoId.toString());

            cityToogle.setAttribute("disabled", "true");
        })
    }


})