// Code for form initialization and submission
window.addEventListener('load', () => init());

function init() {
    let form = document.querySelector("#rekenmachine");
    form.addEventListener("submit", (event) => submitForm(event));
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

// Code for dynamically filling the history table
document.addEventListener("DOMContentLoaded", function() {
    const historyData = [
        { currencyPair: "GBP/JPY", lotSize: 16, date: "1-06-2024" },
        { currencyPair: "GBP/USD", lotSize: 12, date: "13-05-2024" },
        { currencyPair: "EUR/USD", lotSize: 8, date: "06-05-2024" },
        { currencyPair: "EUR/USD", lotSize: 3, date: "24-04-2024" }
    ];

    const historyTableBody = document.getElementById("history-table-body");

    historyData.forEach(entry => {
        const row = document.createElement("tr");

        const currencyPairCell = document.createElement("td");
        currencyPairCell.textContent = entry.currencyPair;
        row.appendChild(currencyPairCell);

        const lotSizeCell = document.createElement("td");
        lotSizeCell.textContent = entry.lotSize;
        row.appendChild(lotSizeCell);

        const dateCell = document.createElement("td");
        dateCell.textContent = entry.date;
        row.appendChild(dateCell);

        const actionCell = document.createElement("td");
        const deleteButton = document.createElement("button");
        deleteButton.className = "delete-button";
        deleteButton.textContent = "🗑️";
        const favoriteButton = document.createElement("button");
        favoriteButton.className = "favorite-button";
        favoriteButton.textContent = "⭐";
        actionCell.appendChild(deleteButton);
        actionCell.appendChild(favoriteButton);
        row.appendChild(actionCell);

        historyTableBody.appendChild(row);
    });
});
