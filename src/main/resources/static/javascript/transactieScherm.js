function transactieCrypto(){
    const input = document.getElementById("transactieCrypto");
    const div = document.createElement("div");
    div.textContent = "{crypto}";
    input.appendChild(div);
}

function cryptoKoers(){
    const input = document.getElementById("transactieKoers");
    let div = document.createElement("div")
    div.textContent = "{koers}";
    input.appendChild(div);
}

function cryptoInPortefeuille(){
    const input = document.getElementById("aantalCryptoInPortefeuille");
    const div = document.createElement("div")
    div.textContent = "{aantal}";
    input.appendChild(div);
}

function bankfee(){
    const input = document.getElementById("transactieBankfee");
    const div = document.createElement("div")
    div.textContent = "{fee}";
    input.appendChild(div);

}

function transactieBedrag(){
    const input = document.getElementById("transactieBedrag");
    const div = document.createElement("div")
    div.textContent = "{totaal bedrag}";
    input.appendChild(div);
}

function wijzigPrijs(){
    let checkbox = document.getElementById("aanpassenTransactiePrijs");
    document.getElementById("transactiePrijsInvoer").value = "100";
    document.getElementById("transactiePrijsInvoer").disabled = true;
    document.getElementById("aanpassenTransactiePrijs").addEventListener("click", function (){
    document.getElementById("transactiePrijsInvoer").value = "100";
    if(checkbox.checked){
        document.getElementById("transactiePrijsInvoer").disabled = false;
        }else {
        document.getElementById("transactiePrijsInvoer").disabled = true;
        }
    })
}

