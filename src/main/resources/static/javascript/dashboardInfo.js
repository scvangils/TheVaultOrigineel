//WIBUL 20220113


function vulCryptoGegevens(data) {
    const mainContainer = document.getElementById("koers");
    const portefeuilleArray = data.portefeuille;
    for (let i = 0; i < portefeuilleArray.length; i++) {
        const div = document.createElement("div");
        const waarde = portefeuilleArray[i].cryptomunt.name;
        console.log(waarde);
        div.textContent = waarde;
        mainContainer.appendChild(div)
    }
}

//JUSEN 20200113

function vulRekeningGegevens(data) {
    document.getElementById("iban").textContent = data.iban;
    document.getElementById("saldo").textContent = data.saldo;
}


