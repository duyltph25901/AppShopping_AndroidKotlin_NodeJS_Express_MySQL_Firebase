// =============== set value default for option in html ======================//
var myDiv = document.querySelector('#category_name').textContent;
const selectElement = document.querySelector("#category");

for (let i = 0; i < selectElement.options.length; i++) {
    if (selectElement.options[i].value == String(myDiv).trim()) {
        selectElement.options[i].selected = true;
        break;
    }
}
// =============== set value default for option in html ======================//