window.addEventListener("load", () => {
    let contactForm = document.getElementById("contactForm")
    let processing = false;
    let contactFormButton = document.getElementById("contactFormButton");
    let userContact = document.getElementsByClassName("userContact");
    let newUserContact = document.getElementById("newUserContact");
    let state = document.getElementById("state");
    let city = document.getElementById("city");
    let street = document.getElementById("street");
    let addressNumber = document.getElementById("addressNumber");
    let floor = document.getElementById("floor");
    let departmentNumber = document.getElementById("departmentNumber");
    let contactId = document.getElementById("contactInfoId");


    contactFormButton.addEventListener("click", () => {
        if (processing) {
            return;
        }
        processing = true;
        contactFormButton.disabled = true;
        contactForm.submit();
        processing = false;
    })

    newUserContact.addEventListener("click",()=>{
        state.textContent = '';
        state.value='';
        state.disabled = false;

        city.textContent = '';
        city.value = '';
        city.disabled = false;

        street.textContent = '';
        street.value = '';
        street.disabled = false;

        addressNumber.textContent = '';
        addressNumber.value = '';
        addressNumber.disabled = false;

        floor.textContent = '';
        floor.value = '';
        floor.disabled = false;

        departmentNumber.textContent = '';
        departmentNumber.value = '';
        departmentNumber.disabled = false;

        contactId.value = -1;
    })

    for (const contact of userContact){
        contact.addEventListener("click", ()=>{
            let info = contact.dataset.info;

            state.textContent = info.state;
            state.value=info.state;
            state.disabled = true;

            city.textContent = info.city;
            city.value = info.city;
            city.disabled = true;

            street.textContent = info.street;
            street.value = info.street;
            street.disabled = true;

            addressNumber.textContent = info.addressNumber;
            addressNumber.value = info.addressNumber;
            addressNumber.disabled = true;

            floor.textContent = info.floor;
            floor.value = info.floor;
            floor.disabled = true;

            departmentNumber.textContent = info.departmentNumber;
            departmentNumber.value = info.departmentNumber;
            departmentNumber.disabled = true;

            contactId.value = info.contactInfoId;
        })
    }



})