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
            console.error('Error:', error);
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
function login(){
    return "";
}