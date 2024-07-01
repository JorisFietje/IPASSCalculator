// Code for form initialization and submission
window.addEventListener('load', () => init());

function init() {
    let form = document.querySelector("#rekenmachine");
    form.addEventListener("submit", (event) => submitForm(event));

    // Call the sortTable function once when the page loads
    sortTable();
}

function submitForm(event) {
    let form = document.querySelector("#rekenmachine");
    let responseDiv = document.querySelector("#serverresponse");
    let data = new URLSearchParams(new FormData(form));

    fetch(form.action + "?" + data.toString(), {method: form.method})
        .then(response => response.text())
        .then(text => responseDiv.innerHTML = text)
        .catch(error => responseDiv.innerHTML = error);

    event.preventDefault();
}

let sortOrder = 1; // 1 for ascending, -1 for descending

function sortTable() {
    const table = document.getElementById('historyTable');
    const rows = Array.from(table.rows).slice(1); // Exclude the header row
    const dateHeader = document.getElementById('dateHeader');

    // Sort the rows based on the "Date" column
    rows.sort((rowA, rowB) => {
        const dateA = new Date(rowA.cells[5].innerText);
        const dateB = new Date(rowB.cells[5].innerText);
        return sortOrder * (dateA - dateB);
    });

    // Remove all current rows
    while (table.rows.length > 1) {
        table.deleteRow(1);
    }

    // Add the sorted rows
    for (const row of rows) {
        table.appendChild(row);
    }

    // Add an arrow to the column header to indicate the sort order
    dateHeader.innerHTML = sortOrder === 1 ? 'Date &darr;' : 'Date &uarr;';

    // Reverse the sort order for the next click
    sortOrder *= -1;
}