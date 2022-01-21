let historieJson;
let koopTransactiesArray;
let verkoopTransactiesArray;

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
const koopTransacties = document.getElementById("fieldsetKoop");
const verkoopTransacties = document.getElementById("fieldSetVerkoop")



async function verwerkHistorie() {
    await getKlantHistorie("LavernRoman");
    koopTransactiesArray = historieJson.transactieLijstGekocht;
    verkoopTransactiesArray = historieJson.transactieLijstVerkocht;
    maakTransactieTabel(koopTransacties, koopTransactiesArray);
    maakTransactieTabel(verkoopTransacties, verkoopTransactiesArray);
}

verwerkHistorie();

let detailsTeZien = 0;

function maakTransactieTabel(element, transactieArray) {
    for (let i = 0; i < transactieArray.length; i++) {
        const transactieDiv = createElementWithClassAndId(element, "div", "transactieField", element.id + (i + 1));
        const inhoud = transactieArray[i];
        const moment = inhoud.momentTransactie.toString();
        transactieDiv.textContent = moment.substring(0, 10) + " " + moment.substring(11);
/*        transactieDiv.classList.add("tooltip");
        const tooltipText = createElementWithClassAndId(transactieDiv, "span", "tooltiptext", transactieDiv.id + "tooltip");
        tooltipText.textContent = "klik voor details";*/
        const popup = createElementWithClassAndId(transactieDiv, "fieldset", "details", transactieDiv.id + "popup");
        popup.textContent = 'Zie ik het?';
        window.onclick = function(event) {
            if (event.target === popup) {
                popup.classList.toggle("show");
            }
        }
        transactieDiv.addEventListener("click", () => {
            popup.classList.toggle("show");
            if(detailsTeZien === 0){
                detailsTeZien++;
            }
            else detailsTeZien--;
        })
    }
}
