function updateDropdown(comp) {
    fetch('itemDropdown?' + new URLSearchParams({storeId : comp.value
    })).then(function (response) {
        return response.text();
    }).then(function (html) {
        document.getElementById("itemsContainer").innerHTML = html;
    }).catch(function (err) {
        console.warn('Something went wrong.', err);
    });
}