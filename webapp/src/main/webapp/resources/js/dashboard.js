let panelTitle = $('#panelTitle');
let dashboard = $('#tabDashboard');
let work = $('#tabWorks');
let contact = $('#tabContacts');

dashboard.click(function(e) {
    e.preventDefault();
    $(this).tab('show')
    panelTitle.html(dashboard.data('name'));
});

work.click(function(e) {
    e.preventDefault();
    $(this).tab('show')
    panelTitle.html(work.data('name'));
});

contact.click(function(e) {
    e.preventDefault();
    $(this).tab('show')
    panelTitle.html(contact.data('name'));
});


