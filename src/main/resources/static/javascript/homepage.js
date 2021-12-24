


const postcodeVeld = document.getElementById("postcode");
const huisnummerVeld = document.getElementById("huisnummer");
const straatnaamVeld = document.getElementById("straatnaam");
const woonplaatsVeld = document.getElementById("plaatsnaam");

postcodeVeld.maxLength = 6;
/*
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
*/

function checkZipCodeFormat(zipCode){
    const pattern = /^[1-9][0-9]{3}[a-z]{2}$/i;
    let checkZipCode = pattern.test(zipCode);
    console.log("Is het een postcode? " + checkZipCode);
    return checkZipCode;
}
function checkHuisnummer(huisnummer){
    const inputNaarNumber =  Number(huisnummer);
    const isNumber = (typeof inputNaarNumber === "number" && inputNaarNumber > 0);
    console.log("Is het een nummer? " + isNumber);
    return isNumber;
}
postcodeVeld.addEventListener("input", postcodeVeldFunctie);
huisnummerVeld.addEventListener("input", postcodeVeldFunctie)

function postcodeVeldFunctie(){
    if(checkZipCodeFormat(postcodeVeld.value) && checkHuisnummer(huisnummerVeld.value)){
        console.log("Ik ga zoeken!")
        vindStraatnaamEnPlaatsnaam();
    }

}


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
    //TODO vragen wat juiste manier is
    }).then((response) => {
        if(response.status === 200) return response.json()
            .then((json) => {
                straatnaamVeld.value = json.street;
                woonplaatsVeld.value = json.city;
            })
        else {
            straatnaamVeld.value = "";
            woonplaatsVeld.value = "";
          //  throw new Error("geen juist huisnummer bij deze postcode")
        }

    })

        .catch((error) => {
            console.error('*** Iets misgegaan:', error);
        });
}

/*TODO Functie Login schrijven*/
function login(){
    return "";
}