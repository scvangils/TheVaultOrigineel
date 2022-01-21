
//WIBUL 20220113
//TODO een designpattern gebruiken voor onderstaande methodes

function sorteerCryptoArray(data){
    const portefeuilleArray = data.portefeuille;
    const sortedPortefeuilleArray = [];
    for (let i = 0; i<portefeuilleArray.length; i++){
        const name = portefeuilleArray[i].name;
        const aantal = portefeuilleArray[i].aantal;
        const prijs = portefeuilleArray[i].prijs;
        const asset = {name: name, aantal: aantal, prijs: prijs}
        sortedPortefeuilleArray.push(asset);
        console.log(sortedPortefeuilleArray)
    }
    sortedPortefeuilleArray.sort(function (a, b){return a.aantal - b.aantal});
}

//TODO naam waarde veranderen naar naam
function vulCryptoGegevens(data){
    console.log(data);
    const mainContainer = document.getElementById("cryptoNaam");
    const portefeuilleArray = data.portefeuille;
    /*const crypto = portefeuilleArray[0];
    console.log(crypto);*/
    for (let i = 0; i < portefeuilleArray.length; i++){
        const div = document.createElement("div");
        const waarde = portefeuilleArray[i].name;
        const crypto = portefeuilleArray[i].afkorting;
        console.log("cryptomunt: " + crypto);
        div.addEventListener("click", function (){alert("Naar Transactiepagina " + waarde);
            nieuweTransactie()})
        console.log(waarde);
        div.textContent = waarde;
        mainContainer.appendChild(div)
    }
}

function vulCryptoAantal(data){
    const mainContainer2 = document.getElementById("cryptoAantal");
    const portefeuilleArray = data.portefeuille;
    for (let i = 0; i < portefeuilleArray.length; i++){
        const div = document.createElement("div");
        const aantal = portefeuilleArray[i].aantal.toFixed(3);
        console.log(aantal);
        div.textContent = aantal;
        mainContainer2.appendChild(div)
    }
}

function vulCryptoKoers(data){
    const mainContainer3 = document.getElementById("cryptoKoers");
    const portefeuilleArray = data.portefeuille;
    for (let i = 0; i < portefeuilleArray.length; i++){
        const div = document.createElement("div");
        const prijs = portefeuilleArray[i].prijs.toFixed(2);
        console.log(prijs);
        div.textContent = prijs;
        mainContainer3.appendChild(div)
    }
}
function vulTotaleWaarde(data){
    const mainContainer4 = document.getElementById("totaleWaarde");
    const portefeuilleArray = data.portefeuille;
    for (let i = 0; i < portefeuilleArray.length; i++){
        const div = document.createElement("div");
        const prijs = portefeuilleArray[i].prijs;
        const aantal = portefeuilleArray[i].aantal;
        let waarde = prijs * aantal;
        div.textContent = waarde.toFixed(2);
        mainContainer4.appendChild(div)
    }
}


//JU-SEN  20220113

function vulRekeningGegevens(data){
    document.getElementById("iban").textContent = "IBAN " + data.iban;
    document.getElementById("saldo").textContent = "Saldo € " + data.saldo.toFixed(2);
}



/** Transactie Fetch
 *  Fetch functie voor het ophalen van de transactiegegevens voor het transactiescherm van een bepaalde
 *  cryptomunt
 * */

function nieuweTransactie(cryptomuntId){
    // haal cryptomunt en gebruikersnaam op
    // ??? waarvandaan???

    const gebruikersnaam = localStorage.getItem("gebruikersnaam");
    console.log("gebruikersnama en cryptomuntId: " + gebruikersnaam, cryptomuntId);

    /*  Hoe voeg je cryptomunt en waarde toe? */
   /* fetch('/transaction', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        /!*???? geef ik nu de juiste informatie mee ? *!/
        //body: JSON.stringify(gebruikersNaamEnCryptomunt),
    })
        .then((response) => {
            if(response.status === 200){
                tranactieScherm();
            }
            console.log('Success:', response);
            return response.json();
        })
        /!*????? wat geef je hier terug???*!/
        .then((json) => {
            transactieCrypto();
            cryptoKoers()
            bankfee()
            transactieBedrag()
            transactieAankoop()
            transactieVerkoop()//
        })
        .catch((error) => {
            console.error('*** Iets misgegaan:', error);
        });*/
}

