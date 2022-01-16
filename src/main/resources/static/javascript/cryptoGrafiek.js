

let grafiekJson;

async function getCryptoKoersInfo(cryptomunt) {
  await fetch("/cryptoGrafiek", {
        method: 'POST',
        headers: {
            'Content-Type': 'text/plain',
        },
        body: cryptomunt,
    })
        .then((response) => {
            return response.json();
        })
      .then((json) => {
          grafiekJson = json;
          return json
      })

        .catch((error) => {
            console.error('*** Iets misgegaan:', error);
        });
}
function getXArray(cryptoHistorieJson){
    const xArray = cryptoHistorieJson.datum;
    return xArray;
}
function getYArray(cryptoHistorieJson){
    const yArray = cryptoHistorieJson.waarde;
    return yArray;
}
let xArray;
let yArray;
let data;
let xArrayMin;
let xArrayMax;
let yArrayMin;
let yArrayMax;
let layout;

async function showCryptoChart(cryptomunt) {

    await getCryptoKoersInfo(cryptomunt);
    xArray = getXArray(grafiekJson);
    yArray = getYArray(grafiekJson);
    xArrayMin = xArray.slice().sort()[0];
    xArrayMax = xArray.slice().sort().reverse()[0];
    yArrayMin = yArray.slice().sort(function (a, b) {
        return a - b
    })[0];
    const yArrayMax = yArray.slice().sort(function (a, b) {
        return b - a
    })[0];
    data = [{
        x: xArray,
        y: yArray,
        mode: "lines"
    }];
    // Define Layout
    layout = {
        xaxis: {
            linecolor: '#636363',
            range: [xArrayMin, xArrayMax],
            type: 'date',
            title: "datum"
        },
        yaxis: {
            linecolor: '#636363',
            range: [yArrayMin * 0.95, yArrayMax * 1.05],
            title: "Koers"
        },
        title: cryptomunt
    };
    Plotly.newPlot(document.getElementById("myPlot"), data, layout);
}

 showCryptoChart("bitcoin");

function setLayout(rangeArray){
    return {
        xaxis: {
            linecolor: '#636363',
            range: rangeArray,
            type: 'date',
            title: "datum"},
        yaxis: {
            linecolor: '#636363',
            range: [yArrayMin * 0.95, yArrayMax * 1.05],
            title: "Koers"},
        title: "Bitcoin"
    };
}
const laatsteWeekKnop = document.getElementById("laatsteWeek");
const laatsteMaandKnop = document.getElementById("laatsteMaand");

laatsteWeekKnop.addEventListener("click", () => {
    const rangeArray = range(laatsteWeekKnop, xArray);
    const layOutNew = setLayout(rangeArray);
    Plotly.newPlot(document.getElementById("myPlot"), data, layOutNew);
})

laatsteMaandKnop.addEventListener("click", () => {
    const rangeArray = range(laatsteMaandKnop, xArray);
    console.log(rangeArray);
    const layOutNew = setLayout(rangeArray);
    Plotly.newPlot(document.getElementById("myPlot"), data, layOutNew);
})

function range(element, dataArray){
    switch (element.value){
        case "Laatste week": return [dataArray[dataArray.length - 8], dataArray[dataArray.length - 1]];

        case "Laatste 30 dagen": return [dataArray[dataArray.length - 31], dataArray[dataArray.length - 1]];

    }
}

