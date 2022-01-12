//TODO placeholder toevoegen (eventueel verplicht toevoegen?)
class InputMetLabel{
  constructor(naam, id, type) {
      this.naam = naam;
      this.id = id;
      this.type = type;
  }
}

// TODO combineren tot 1 inputArray
const inputArrayPersoon = [new InputMetLabel("naam", "naam", "text"),
    new InputMetLabel("gebruikersnaam", "gebruikersnaam", "text"),
    new InputMetLabel("geboortedatum", "geboortedatum", "date"),
    new InputMetLabel("bsn", "BSN", "number"),
    new InputMetLabel("wachtwoord", "wachtwoord", "password")]

const inputArrayAdres = [
    new InputMetLabel("postcode", "postcode", "text"),
    new InputMetLabel("huisnummer", "huisnummer", "number"),
    new InputMetLabel("toevoeging", "toevoeging","text"),
    new InputMetLabel("straatnaam", "straatnaam","text"),
    new InputMetLabel("plaatsnaam", "plaatsnaam","text")
]
const NEDERLANDSE_POSTCODE_LENGTE = 6;
// ***** HIER NIEUWE FILE

function createElementWithClassAndId(parent, tag, elementClass, elementId){
    const element = document.createElement(tag);
    element.classList.add(elementClass);
    element.setAttribute("id", elementId);
    return element;
}

function createElementAtParent(element, type, parent, idName, naam) {
    const createdElement = document.createElement(element);
    createdElement.setAttribute("id", idName);
    createdElement.setAttribute("type", type);
    createdElement.setAttribute("name", naam);
    parent.appendChild(createdElement);
    return createdElement;
}
function createLabel(parent, forInput, tekst){
    const newLabel = document.createElement("label");
    newLabel.htmlFor = forInput;
    parent.appendChild(newLabel).appendChild(document.createTextNode(tekst));
    return newLabel;
}

function createLabelAndInputAtParent(parent, inputMetLabel){
    createLabel(parent, inputMetLabel.id, inputMetLabel.id);
    createElementAtParent("input", inputMetLabel.type, parent, inputMetLabel.id, inputMetLabel.naam);
}
// TODO creeer function createFieldSetForm()
//zoek de parent-div
const hoofdFormulier = document.getElementById("registratie-personalia");

function createFieldSetForm(parent, legendText, formId, fieldsetId){
    const registratieForm = document.createElement("form");
    registratieForm.setAttribute("id", formId);
    const fieldSetOfForm = document.createElement("FIELDSET");
    fieldSetOfForm.classList.add("flex-container")
    fieldSetOfForm.setAttribute("id", fieldsetId);
    const registratieLegend = document.createElement("LEGEND");
    registratieLegend.textContent = legendText;
    fieldSetOfForm.appendChild(registratieLegend);
    registratieForm.appendChild(fieldSetOfForm);
    hoofdFormulier.appendChild(registratieForm);
}

//creëer form voor alles
const registratieForm = document.createElement("form");
registratieForm.setAttribute("id", "registratieOverig");
const fieldSetOfForm = document.createElement("FIELDSET");
fieldSetOfForm.classList.add("flex-container")
const registratieLegend = document.createElement("LEGEND");
registratieLegend.textContent = "vul hier uw Gegevens in";
fieldSetOfForm.appendChild(registratieLegend);
registratieForm.appendChild(fieldSetOfForm);
hoofdFormulier.appendChild(registratieForm);


function fillFormOneColumn(parent, inputArrayEen, inputArrayTwee) {
    for (let i = 0; i < inputArrayEen.length; i++) {
        createLabelAndInputAtParent(parent, inputArrayEen[i])
    }
    for (let i = 0; i < inputArrayTwee.length; i++) {

        createLabelAndInputAtParent(parent, inputArrayTwee[i])
    }
}

function fillFormTwoColumnsOriginal(parent, inputArray1, inputArray2){

    for (let i = 0; i < inputArray1.length; i++) {
        const divRow = document.createElement("div");
        divRow.classList.add("row");
        parent.appendChild(divRow);
            const divColPersoonLabel = createLabel(parent, inputArray1[i].id, inputArray1[i].id)
            divColPersoonLabel.classList.add("col");
            divRow.appendChild(divColPersoonLabel);
            const divColAdresLabel = createLabel(parent, inputArray2[i].id, inputArray2[i].id)
            divColAdresLabel.classList.add("col");
            divRow.appendChild(divColAdresLabel);
        const divRow2 = document.createElement("div");
        divRow2.classList.add("row");
        parent.appendChild(divRow2);
            const divColPersoonInput = createElementAtParent("input", inputArray1[i].type, parent, inputArray1[i].id, inputArray1[i].naam);
            divColPersoonInput.classList.add("col");
            divRow2.appendChild(divColPersoonInput);
            const divColAdresInput = createElementAtParent("input", inputArray2[i].type, parent, inputArray2[i].id, inputArray2[i].naam);
            divColAdresInput.classList.add("col");
            divRow2.appendChild(divColAdresInput);
    }
}

function fillFormColumn(parent, inputArray, colName){
        const divColumn = document.createElement("div");
        divColumn.classList.add("row");
        parent.appendChild(divColumn);
        divColumn.setAttribute("id", colName);
        for (let j = 0; j < inputArray.length; j++) {

            const divRowLabel = createLabel(parent, inputArray[j].id, inputArray[j].id);
            divRowLabel.classList.add("col");

            divColumn.appendChild(divRowLabel);
            const divColumnInput = createElementAtParent("input", inputArray[j].type, parent, inputArray[j].id, inputArray[j].naam);
            divColumnInput.classList.add("col");
            divColumn.appendChild(divColumnInput);
        }
}

const registratieFormulier = document.createElement("div");
registratieFormulier.classList.add("flexBasis");
fieldSetOfForm.appendChild(registratieFormulier);
fillFormColumn(registratieFormulier, inputArrayPersoon, "persoon");
fillFormColumn(registratieFormulier, inputArrayAdres, "adres");

// fillFormTwoColumnsOriginal(fieldSetOfForm, inputArrayPersoon, inputArrayAdres);

// beperk velden op de juiste manier
const postcodeVeld = document.getElementById("postcode");
postcodeVeld.maxLength = NEDERLANDSE_POSTCODE_LENGTE;
const huisnummerVeld = document.getElementById("huisnummer");
const straatnaamVeld = document.getElementById("straatnaam");
straatnaamVeld.setAttribute("disabled", "true")
const woonplaatsVeld = document.getElementById("plaatsnaam");
woonplaatsVeld.setAttribute("disabled", "true");

// Deze knop opent de modal
//TODO CSS regelen
const openModalButton = createElementAtParent("button", "button", fieldSetOfForm, "checkGegevensBtn", "checkGegevensBtn" );
openModalButton.innerHTML = "Registreer";
openModalButton.classList.add("button");



// TODO Modal in javascript bouwen
const modal = document.getElementById("myModal");
/*
const modalContent = createElementWithClassAndId(modal, "div", "modal-header", "modalHeader");

const modalHeader = createElementWithClassAndId(modalContent, "div", "modal-header", "modalHeader");
const span = createElementWithClassAndId(modalHeader, "span", "close", "closeSpan");
const modalHeaderText = document.createElement("h2");
modalHeaderText.textContent = "Kloppen alle gegevens?";
modalHeader.appendChild(modalHeaderText);
const modalBody = createElementWithClassAndId(modalContent, "div", "modal-body", "modalBody");
const modalFooter = createElementWithClassAndId(modalContent, "div", "modal-footer", "modalFooter");
const modalButton = createElementAtParent();
*/


// Get the <span> element that closes the modal
const span = document.getElementsByClassName("close")[0];

 const modalBody = document.getElementById("modalBody");

//TODO onfocus van input vervolgen
//TODO magic word verwijderen
//checkt of alle noodzakelijke velden zijn ingevuld
function checkInput(inputElement){
    if(inputElement.value === "" && inputElement.name !== "toevoeging"){
        if(inputElement.id === "wachtwoord"){
            inputElement.parentElement.style.borderColor = "red";
        }
        else inputElement.style.borderColor = "red";
        inputElement.setAttribute("placeholder", "verplicht veld");
        return false;
    }
    else inputElement.style.borderColor = "lightgrey";
    return true;
}
//div wordt nu opgelost door border-color op focus
//TODO div met outline uitrusten?
//haalt rode border weg als veld wordt aangeklikt
for(const element of document.getElementsByTagName("input")){
    element.addEventListener("input", () => {
        element.style.borderColor = "lightgrey";
        element.setAttribute("placeholder", "");
    })
}

//TODO check op geboortedatum en BSN(?)

function checkAllInputs(){
    let correcteInvoer = true;
    for (let i = 0; i < inputArrayPersoon.length; i++) {
    correcteInvoer = checkInput(document.getElementById(inputArrayPersoon[i].id));
    }
    for (let i = 0; i < inputArrayAdres.length; i++) {
    correcteInvoer = checkInput(document.getElementById(inputArrayAdres[i].id));
    }
    return correcteInvoer;
}

function maakModalBodyLeeg(){
    while (modalBody.firstChild) {
        modalBody.removeChild(modalBody.firstChild);
    }
}
//Zorg ervoor dat de modal eerst leeg wordt gemaakt i.v.m. eerdere pogingen
const modalFunctie = () => {
    maakModalBodyLeeg();
    fillModalBody(inputArrayPersoon);
    fillModalBody(inputArrayAdres)
    modal.style.display = "block";
}
function gegevensHandler(){
if(checkAllInputs()){
    modalFunctie();
}
}
openModalButton.addEventListener("click", gegevensHandler);
function registratieModalDicht(){
    modal.style.display = "none";
}

// When the user clicks on <span> (x), close the modal
span.addEventListener("click", registratieModalDicht);

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target === modal) {
        registratieModalDicht();
    }
}

//modal laten vullen door inputvelden via flex-box-tabelstructuur
//wachtwoord niet laten zien
function fillModalBody(inputArray){
    for (let i = 0; i < inputArray.length; i++) {
        if (inputArray[i].naam !== "wachtwoord") {
        const divRow = document.createElement("div");
        divRow.classList.add("rowModal");
        modalBody.appendChild(divRow);
        const divColName = document.createElement("div");
        divColName.classList.add("col");
        divRow.appendChild(divColName).appendChild(document.createTextNode(inputArray[i].naam));
        const divColValue = document.createElement("div");
        divColValue.classList.add("col");
        const waarde = document.createTextNode(document.getElementById(inputArray[i].id).value);
        divRow.appendChild(divColValue).appendChild(waarde);
    }
    }
}



// **** HIER NIEUWE FILE

//vervang wachtwoord-veld door div met div met input en span als children
//verdeel input en image over div
//grootte div en children laten bepalen door ander input-text-element
    const passwordDiv = document.createElement("div");
    passwordDiv.setAttribute("id", "passwordDiv");
    passwordDiv.style.width = (document.getElementById("naam").offsetWidth).toString() + "px";

    //waarom werkt dit niet?
/*const hoogte = document.getElementById("naam").offsetHeight;
console.log(hoogte.toString());
    passwordDiv.style.height = (document.getElementById("naam").offsetHeight).toString() + "px";*/
    passwordDiv.style.height = "23px";
//zorgen dat div er als input uitziet
//TODO kleur en breedte bepalen via andere input of alle inputs hetzelfde zetten?
    passwordDiv.style.borderColor = "lightgrey";
    passwordDiv.style.borderWidth = "2px";
//identiek element creëren, misschien klonen?
    const passwordInput = document.createElement("input");
    passwordInput.setAttribute("type", "password");
    passwordInput.setAttribute("id", "wachtwoord");

//TODO deels via css regelen
    const hoverEye = document.createElement("span");
    hoverEye.setAttribute("id", "toonWachtwoord");
    hoverEye.style.width = (document.getElementById("naam").offsetWidth * 0.2).toString() + "px";
    hoverEye.style.height = "100%";
//voeg input en span toe aan div
    passwordDiv.appendChild(passwordInput);
    passwordDiv.appendChild(hoverEye);
// hier wachtwoordveld vervangen door div
    document.getElementById("wachtwoord").replaceWith(passwordDiv);

//TODO deels via css regelen
    const wachtwoordVeld = document.getElementById("wachtwoord");
    wachtwoordVeld.style.width = (document.getElementById("naam").offsetWidth * 0.8).toString() + "px";
    wachtwoordVeld.style.alignSelf = "start";
    wachtwoordVeld.style.borderRight = "none";

//bij hover over oog password laten zien
    hoverEye.addEventListener("mouseover", showPassword);
    hoverEye.addEventListener("mouseleave", hidePassword);

    function showPassword() {
        const passwordField = document.getElementById("wachtwoord");
        passwordField.setAttribute("type", "text");
    }
    function hidePassword() {
        const passwordField = document.getElementById("wachtwoord");
        passwordField.setAttribute("type", "password");
    }

//zorgen dat div ook blauwe border krijgt bij focus
wachtwoordVeld.addEventListener("focusin", showDivOutline);
wachtwoordVeld.addEventListener("focusout", hideDivOutline);

    function showDivOutline() {
        passwordDiv.style.borderColor = "blue";
        passwordDiv.style.backgroundColor = "aliceblue";
    }
    function hideDivOutline() {
        passwordDiv.style.borderColor = "lightgrey";
        passwordDiv.style.backgroundColor = "transparent";
    }


// ***** HIER NIEUWE FILE

//straatnaam en plaatsnaam automatisch laten zoeken
//pas zoeken als postcode correct format heeft en er een nummer is ingevuld
    function checkZipCodeFormat(zipCode) {
        const pattern = /^[1-9][0-9]{3}[a-z]{2}$/i; // i maakt het capital insensitive
        return pattern.test(zipCode);
    }

    function checkHuisnummer(huisnummer) {
        const inputNaarNumber = Number(huisnummer);
        return (typeof inputNaarNumber === "number" && inputNaarNumber > 0);
    }

    postcodeVeld.addEventListener("input", postcodeVeldFunctie);
    huisnummerVeld.addEventListener("input", postcodeVeldFunctie)

    function postcodeVeldFunctie() {
        if (checkZipCodeFormat(postcodeVeld.value) && checkHuisnummer(huisnummerVeld.value)) {
            vindStraatnaamEnPlaatsnaam();
        }
    }
function vindStraatnaamEnPlaatsnaam() {
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
        if (response.status === 200) return response.json()
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
// Deze functie maakt objecten met behulp van een inputArray
function maakObject(inputArray) {
    const object = {};
    for (let i = 0; i < inputArray.length; i++) {
        const naam = inputArray[i].naam;
        const waarde = document.getElementById(inputArray[i].id).value;
        object[naam] = waarde;
    }
    return object;
}

function maakCompleetKlantObject(){
    const adresObject = maakObject(inputArrayAdres);
    adresObject.adresId = 0;
    const klantObject = maakObject(inputArrayPersoon);
    klantObject.gebruikerId = 0;
    klantObject.rekening = null;
    klantObject.portefeuille = null;
    klantObject.adres = adresObject;
    return klantObject;
}

    function registreer() {
            fetch('/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(maakCompleetKlantObject()),
        })
            .then((response) => {
                if (response.status === 201)
                    return response.json()
                        // Toon "gelukt" o.i.d. in modal
                        // Sluit modal en toon inlog
                        .then((json) => showWelcomeMessage(json))
                else return response.json()
                    .then((json) => {
                        const modalFout = toonMelding();
                        modalFout.textContent = json.message;
                    })
            })
            .catch((error) => {
                console.error('*** Iets misgegaan:', error);
            });
    }

//
function showWelcomeMessage(json){
        loginScreen();
    maakModalBodyLeeg();
    document.getElementById("modalText").textContent = "Welkom bij The Vault!";
    document.getElementById("verstuur").value = "Naar de Inlogpagina";
    //TODO dit oplossen
    document.getElementById("verstuur").removeEventListener("click", registreer);
    document.getElementById("verstuur").addEventListener("click", registratieModalDicht);
    const waarden = Object.values(json);
    for(let waarde of waarden){
        console.log(waarde);
    const modalTekst = toonMelding();
    modalTekst.textContent = waarde;
    }
}

function toonMelding(){

    const melding = document.createElement("div");
    modalBody.appendChild(melding);
    return melding;
}
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

/*
        const formDataAdres = new FormData(document.getElementById('registratieAdres'));
        const adres = Object.fromEntries(formDataAdres);
        adres.adresId = 0;
        const formData = new FormData(document.getElementById('registratieOverig'));
        const klant = Object.fromEntries(formData);
        klant.gebruikerId = 0;
        klant.wachtwoord = document.getElementById("wachtwoord").value;
        klant.rekening = null;
        klant.portefeuille = null;
        klant.adres = adres;

*/


//code om een popup te maken
/*const popupDiv = document.createElement("label");
document.body.appendChild(popupDiv);
popupDiv.classList.add("popup");
popupDiv.textContent = "click me!"
popupDiv.addEventListener("click", toonIncorrecteGegevens);
const popupSpan = document.createElement("span");
popupSpan.classList.add("popuptext");
popupSpan.setAttribute("id", "incorrecteGegevensPopup");
popupDiv.appendChild(popupSpan);*/