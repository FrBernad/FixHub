let panelTitle = $('#panelTitle');
let dashboard = $('#tabDashboard');
let work = $('#tabWorks');
let contact = $('#tabContacts');

dashboard.click(function(e) {
    console.log('dashboard');
    e.preventDefault();
    $(this).tab('show')
    panelTitle.html(dashboard.data('name'));
    console.log(panelTitle.html());
    console.log($(this));
});

work.click(function(e) {
    console.log('work');
    e.preventDefault();
    $(this).tab('show')
    panelTitle.html(work.data('name'));
});

contact.click(function(e) {
    console.log('contacts');
    e.preventDefault();
    $(this).tab('show')
    panelTitle.html(contact.data('name'));
});


