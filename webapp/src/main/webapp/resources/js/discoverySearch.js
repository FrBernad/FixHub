function setFilterValueAndSubmit(filter) {
    document.getElementById("filterInput").setAttribute("value", filter);
    submitSearchForm();
}

function setOrderValueAndSubmit(order) {
    document.getElementById("orderInput").setAttribute("value", order);
    submitSearchForm();
}

function setSearchValueAndSubmit() {
    const query = document.getElementById("searchBar").value;
    document.getElementById("searchInput").setAttribute("value", query);
    submitSearchForm();
}


function submitSearchForm() {
  document.getElementById("searchForm").submit();
}