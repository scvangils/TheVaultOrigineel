const labels = document.getElementsByTagName("label");
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
 //   const formDataString = JSON.stringify(Object.fromEntries(formData));



    fetch('/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(klant),
    })
        .then(response => {
            console.log('Success:', response);
        })
        .catch((error) => {
            console.error('Error:', error);
        });

}