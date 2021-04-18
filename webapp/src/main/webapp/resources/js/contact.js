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
        if(document.getElementById("dropdownMenuButton") == null)
            contactId.setAttribute("value","-1");

        processing = true;
        contactFormButton.disabled = true;
        contactForm.submit();
        processing = false;
    })

    if(newUserContact !== null){
        newUserContact.addEventListener("click",()=>{
            state.textContent = '';
            state.setAttribute("value", '');
            state.disabled = false;

            city.textContent = '';
            city.setAttribute("value", '');
            city.disabled = false;

            street.textContent = '';
            street.setAttribute("value", '');
            street.disabled = false;

            addressNumber.textContent = '';
            addressNumber.setAttribute("value", '');
            addressNumber.disabled = false;

            floor.textContent = '';
            floor.setAttribute("value", '');
            floor.disabled = false;

            departmentNumber.textContent = '';
            departmentNumber.setAttribute("value", '');
            departmentNumber.disabled = false;

            contactId.setAttribute("value", "-1");

        })
    }


    for (const contact of userContact){
        contact.addEventListener("click", ()=>{
            let info = contact.dataset;
            state.textContent = info.state;
            state.setAttribute("value", info.state.toString());
            state.disabled = true;

            city.textContent = info.city;
            city.setAttribute("value", info.city.toString());
            city.disabled = true;

            street.textContent = info.street;
            street.setAttribute("value", info.street.toString());
            street.disabled = true;

            addressNumber.textContent = info.addressNumber;
            addressNumber.setAttribute("value", info.addressNumber.toString());
            addressNumber.disabled = true;

            floor.textContent = info.floor;
            floor.setAttribute("value", info.floor.toString());
            floor.disabled = true;

            departmentNumber.textContent = info.departmentNumber;
            departmentNumber.setAttribute("value", info.departmentNumber.toString());
            departmentNumber.disabled = true;

            contactId.setAttribute("value", info.contactInfoId.toString());

        })
    }



})