let historieJson;
let koopTransactiesArray;
let verkoopTransactiesArray;
let koopTriggersArray;
let verkoopTriggersArray;


async function getKlantHistorie(gebruikersnaam) {
  //  await validateJWT(jwt, gebruikersnaam);
  await fetch("/klantHistorie",{
        method: 'post',
        headers: {
            'Content-Type': 'text/plain',
        },
        body: gebruikersnaam
    }).then((response) => response.json())
      .then((json) => {
          historieJson = json;
      })
      .catch((error) => {
          console.error('*** Iets misgegaan:', error);
      });
}
const koopTransacties = document.getElementById("fieldsetKoopTransactie");
const verkoopTransacties = document.getElementById("fieldsetVerkoopTransactie")




async function verwerkHistorie() {
    await getKlantHistorie("LavernRoman");
    koopTransactiesArray = historieJson.transactieLijstGekocht;
    verkoopTransactiesArray = historieJson.transactieLijstVerkocht;
    koopTriggersArray = historieJson.koopTriggers;
    verkoopTriggersArray = historieJson.verkoopTriggers;
    maakTransactieTabel(koopTransacties, koopTransactiesArray);
    maakTransactieTabel(verkoopTransacties, verkoopTransactiesArray);
    document.getElementById("detailVerkoopTransactie").parentNode.appendChild(document.getElementById("detailVerkoopTransactie"));
    document.getElementById("detailKoopTransactie").parentNode.appendChild(document.getElementById("detailKoopTransactie"));
    maakTriggerTabel(koopTriggers, koopTriggersArray);
    maakTriggerTabel(verkoopTriggers, verkoopTriggersArray);
    document.getElementById("detailVerkoopTrigger").parentNode.appendChild(document.getElementById("detailVerkoopTrigger"));
    document.getElementById("detailKoopTrigger").parentNode.appendChild(document.getElementById("detailKoopTrigger"));


}

verwerkHistorie();


function maakTransactieTabel(element, transactieArray) {
    let detailDiv;
    if (element.id === "fieldsetKoopTransactie") {
        detailDiv = createElementWithClassAndId(koopTransacties, "div", "details", "detailKoopTransactie");
    }
    if (element.id === "fieldsetVerkoopTransactie") {
        detailDiv = createElementWithClassAndId(verkoopTransacties, "div", "details", "detailVerkoopTransactie");
    }
    const detailFieldset = createElementWithClassAndId(detailDiv, "fieldset", "flex-container", element.id + "Set");
    for (let i = 0; i < transactieArray.length; i++) {

        const transactieDiv = createElementWithClassAndId(element, "div", "historieField", element.id + (i + 1));
        const inhoud = transactieArray[i];
        const hoeveelCrypto = inhoud.aantal + " " + inhoud.cryptomunt.name
        const moment = inhoud.momentTransactie.toString();
        transactieDiv.textContent = hoeveelCrypto
        transactieDiv.classList.add("tooltip");
        const tooltipText = createElementWithClassAndId(transactieDiv, "span", "tooltiptext", transactieDiv.id + "tooltip");
        tooltipText.textContent = "klik voor details";
        transactieDiv.addEventListener("click", () => {
                if (document.getElementById("klantHistorie").getElementsByClassName("show").length === 0) {
                    maakDetailBodyLeeg(detailFieldset)
                    detailDiv.classList.toggle("show");
                    detailDiv.addEventListener("click", () => {
                        detailDiv.classList.remove("show");
                    })
                    const datumDetail = createElementWithClassAndId(detailFieldset, "div", "row", detailFieldset.id + "datum");
                    const muntDetail = createElementWithClassAndId(detailFieldset, "div", "row", detailFieldset.id + "munt");
                    const prijsDetail = createElementWithClassAndId(detailFieldset, "div", "row", detailFieldset.id + "prijs");
                    const partijDetail = createElementWithClassAndId(detailFieldset, "div", "row", detailFieldset.id + "partij");
                    const bedragDetail = createElementWithClassAndId(detailFieldset, "div", "row", detailFieldset.id + "bedrag");
                    const bankFeeDetail = createElementWithClassAndId(detailFieldset, "div", "row", detailFieldset.id + "bankFee");
                    datumDetail.textContent = moment.substring(0, 10) + " " + moment.substring(11);
                    muntDetail.textContent = hoeveelCrypto;
                    let afgerond = inhoud.prijs.toFixed(2);
                    prijsDetail.textContent = "prijs per eenheid\r\n\u20AC" + afgerond;
                    if (element.id === "fieldsetKoopTransactie") {
                        if(inhoud.verkoper.gebruikerId !== 0) {
                            partijDetail.textContent = "gekocht van" + inhoud.verkoper.rekening.iban;
                        }
                        else partijDetail.textContent = "gekocht van\r\nThe Vault";
                    }
                    if (element.id === "fieldsetVerkoopTransactie") {
                        if(inhoud.koper.gebruikerId !== 0) {
                            partijDetail.textContent = "verkocht aan\r\n" + inhoud.koper.rekening.iban;
                        }
                        else partijDetail.textContent = "verkocht aan\r\nThe Vault";
                    }
                    let bedrag = (inhoud.prijs * inhoud.aantal).toFixed(2);
                    bedragDetail.textContent = "voor\r\n\u20AC" + bedrag;
                    bankFeeDetail.textContent = "fee: \u20AC" + inhoud.bankFee;
                }
                else{
                    const element = document.getElementById("klantHistorie").getElementsByClassName("show")[0];
                    element.classList.toggle("show");

                }
            }
        )

    }

}
function maakDetailBodyLeeg(detail) {
    while (detail.firstChild) {
        detail.removeChild(detail.firstChild);
    }
}

const koopTriggers = document.getElementById("fieldsetKoopTrigger");
const verkoopTriggers = document.getElementById("fieldsetVerkoopTrigger")

function maakTriggerTabel(element, triggerArray) {
    let detailDiv;
    if (element.id === "fieldsetKoopTrigger") {
        detailDiv = createElementWithClassAndId(koopTriggers, "div", "details", "detailKoopTrigger");
    }
    if (element.id === "fieldsetVerkoopTrigger") {
        detailDiv = createElementWithClassAndId(verkoopTriggers, "div", "details", "detailVerkoopTrigger");
    }
    const detailFieldset = createElementWithClassAndId(detailDiv, "fieldset", "flex-container", element.id + "Set");
    for (let i = 0; i < triggerArray.length; i++) {

        const triggerDiv = createElementWithClassAndId(element, "div", "historieField", element.id + (i + 1));
        const inhoud = triggerArray[i];
        const hoeveelCrypto = inhoud.aantal + " " + inhoud.cryptomunt.name
        const moment = inhoud.datum;
        triggerDiv.textContent = hoeveelCrypto
        triggerDiv.classList.add("tooltip");
        const tooltipText = createElementWithClassAndId(triggerDiv, "span", "tooltiptext", triggerDiv.id + "tooltip");
        tooltipText.textContent = "klik voor details";
        triggerDiv.addEventListener("click", () => {
                if (document.getElementById("klantHistorie").getElementsByClassName("show").length === 0) {
                    maakDetailBodyLeeg(detailFieldset)
                    detailDiv.classList.toggle("show");
                    detailDiv.addEventListener("click", () => {
                        detailDiv.classList.remove("show");
                    })
                    const datumDetail = createElementWithClassAndId(detailFieldset, "div", "row", detailFieldset.id + "datum");
                    const muntDetail = createElementWithClassAndId(detailFieldset, "div", "row", detailFieldset.id + "munt");
                    const prijsDetail = createElementWithClassAndId(detailFieldset, "div", "row", detailFieldset.id + "prijs");
                    const bedragDetail = createElementWithClassAndId(detailFieldset, "div", "row", detailFieldset.id + "bedrag");
                    datumDetail.textContent = moment;
                    muntDetail.textContent = hoeveelCrypto;
                    let afgerond = inhoud.triggerPrijs.toFixed(2);
                    if (element.id === "fieldsetKoopTrigger") {
                        prijsDetail.textContent = "wenst te kopen voor\n"
                        prijsDetail.textContent += "prijs per eenheid\r\n\u20AC" + afgerond;
                    }
                    if (element.id === "fieldsetVerkoopTrigger") {
                        prijsDetail.textContent = "aangeboden voor\n"
                        prijsDetail.textContent += "prijs per eenheid\r\n\u20AC" + afgerond;
                    }
                    let bedrag = (inhoud.triggerPrijs * inhoud.aantal).toFixed(2);
                    bedragDetail.textContent = "Totaal:\r\n\u20AC" + bedrag;
                }
                else{
                    const element = document.getElementById("klantHistorie").getElementsByClassName("show")[0];
                    element.classList.toggle("show");

                }
            }
        )

    }

}
