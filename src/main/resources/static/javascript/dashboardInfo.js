
//WIBUL 20220113


function vulCryptoGegevens(data){
    const mainContainer = document.getElementById("cryptoNaam");
    const portefeuilleArray = data.portefeuille;
    for (let i = 0; i < portefeuilleArray.length; i++){
        const div = document.createElement("div");
        const waarde = portefeuilleArray[i].cryptomunt.name
            // + " " + portefeuilleArray[i].aantal;
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
        const aantal = portefeuilleArray[i].aantal;
        console.log(aantal);
        div.textContent = aantal;
        mainContainer2.appendChild(div)
    }
}

//JU-SEN  20220113

function vulRekeningGegevens(data){
    document.getElementById("iban").textContent = data.iban;
    document.getElementById("saldo").textContent = data.saldo;
}

