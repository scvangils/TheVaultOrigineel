"use strict";
/*const labels = document.getElementsByTagName("label");
for (const label of labels) {
    label.classList.add("grid-item");
}
const inputs = document.getElementsByTagName("input");
for (const input of inputs) {
    input.classList.add("grid-item");
}
const forms = document.getElementsByTagName("form");
for (const form of forms) {
    form.classList.add("grid-container");
}*/
const postcodeVeld = document.getElementById("postcode");
const huisnummerVeld = document.getElementById("huisnummer");
const straatnaamVeld = document.getElementById("straatnaam");
const woonplaatsVeld = document.getElementById("plaatsnaam");

function addClassToTag(tagName, cssClass){
    const elements = document.getElementsByTagName(tagName);
    for (const element of elements) {
        element.classList.add(cssClass);
    }
}
function removeClassFromElementById(elementId, cssClass){
    document.getElementById(elementId).classList.remove(cssClass);
}


addClassToTag("form", "grid-container");
addClassToTag("label", "grid-item");
addClassToTag("input", "grid-item");
removeClassFromElementById("verstuur", "grid-item");


function registreer(){
    const formDataAdres = new FormData(document.getElementById('registratieAdres'));
    const adres = Object.fromEntries(formDataAdres);
    adres.adresId = 0;
    const formData = new FormData(document.getElementById('registratieOverig'));
    const klant = Object.fromEntries(formData);
    klant.gebruikerId = 0;
    klant.rekening = null;
    klant.portefeuille = null;
    klant.adres = adres;

    fetch('/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(klant),
    })
        .then((response) => {
            console.log('Success:', response);
        })
        .catch((error) => {
            console.error('*** Iets misgegaan:', error);
        });
}
function vindStraatnaamEnPlaatsnaam(){
    const postcode = postcodeVeld.value;
    const huisnummer = huisnummerVeld.value;
    const apiKey = "7b7ea14e-5494-481b-b36c-cb40ebb63c62";
    const url = `https://postcode.tech/api/v1/postcode?postcode=${postcode}&number=${huisnummer}`;
    fetch(url, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${apiKey}`,
        }

    }).then((response) => response.json())
        .then((json) => {
            straatnaamVeld.value = json.street;
            woonplaatsVeld.value = json.city;
        })
}

/*TODO Functie Login schrijven*/
/* 1- Gegevens van website ophalen */
/* 2- Gegevens doorsturen naar het juiste endpoint */
/* 3- Vanuit het endpoint moet een check worden uitgevoerd op naam en wachtwoord */
/* 3a-Hier staat informatie over de authenticate header: https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/WWW-Authenticate*/
/* 3a-Moet ik hiervoor het schema HMAC256 gebruiken? Zie ook 'Authorization' header van vindstraatenplaatsnaam. */
/* 4a-Als 'valid' dan moet gebruiker worden doorgestuurd naar dashboard */
/* 4b-En er moet een token worden meegegeven die wordt opgeslagen, via sessionStorage.setItem("sessietoken", "token")? */

function login(){
    const formDataInlog = new FormData(document.getElementById("inlogform"));
    const inlogGegevens = Object.fromEntries(formDataInlog);

    /*Hoe werkt deze fetch? Hoe weet ik of de API werkt?*/
    fetch('/inlog', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        /*Is dit zo veilig? Via 'FormData' zouden de gegevens al encoded moeten zijn, is dat voldoende?*/
        body: JSON.stringify(inlogGegevens),
    })
        .then((response) => {
            console.log('Success:', response);
        })
        .catch((error) => {
            console.error('*** Iets misgegaan:', error);
        });
}

/*TODO (Singleton?) Mediator schrijven voor gebruiken login vs registratie? Zie voorbeeld*/
/* https://refactoring.guru/design-patterns/mediator */
/*TODO (Singleton?) Façade pattern voor de bank: de klant ziet alleen de 'voorkant', maar achter de schermen gebeurt veel. Voorbeeld:*/
/* https://www.youtube.com/watch?v=B1Y8fcYrz5o&t=50s */
/* Om met nummers te werken: https://www.w3schools.com/js/js_number_methods.asp*/
/* RegExp: https://www.w3schools.com/js/js_regexp.asp en https://www.w3schools.com/jsref/jsref_obj_regexp.asp */
/* Closures in JavaScript: https://developer.mozilla.org/en-US/docs/Web/JavaScript/Closures */
/* Simpele uitleg closures: https://dmitripavlutin.com/simple-explanation-of-javascript-closures/*/
/* Asymetrisch met promise en await: https://www.w3schools.com/js/js_async.asp*/