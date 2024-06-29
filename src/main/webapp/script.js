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

document.getElementById('copyButton').addEventListener('click', function() {
    // Create a new textarea element and give it id='temp_element'
    var textarea = document.createElement('textarea')
    textarea.id = 'temp_element'
    // Optional step to make less noise on the page, if any!
    textarea.style.height = 0
    // Now append it to your page somewhere, I chose <body>
    document.body.appendChild(textarea)
    // Give our textarea a value of whatever inside the div of id='content'
    textarea.value = document.querySelector('.lotSize').innerText
    // Now copy whatever inside the textarea to clipboard
    var selector = document.querySelector('#temp_element')
    selector.select()
    document.execCommand('copy')
    // Remove the textarea
    document.querySelector('#temp_element').remove()
});