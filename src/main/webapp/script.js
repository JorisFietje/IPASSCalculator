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
