function transactieCrypto(json){
    const input = document.getElementById("transactieCrypto");
    const div = document.createElement("div");
    div.textContent = "{crypto}";
    input.appendChild(div);
}


function cryptoKoers(json){
    const input = document.getElementById("transactieKoers");
    const div = document.createElement("div")
    div.textContent = "{koers}";
    input.appendChild(div);
}

function bankfee(){
    //op basis van handel met bank of klanten onderling
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

function transactieAankoop(){
}

function transactieVerkoop(){

}

