

function getXArray(json){
    const xArray = json.datum;
    console.log(xArray);
    return xArray;
}
function getYArray(json){
    const yArray = json.waarde;
    console.log(yArray);
    return yArray;
}


const xArray = [
    "2021-09-12",
    "2021-09-13",
    "2021-09-14",
    "2021-09-15",
    "2021-09-16",
    "2021-09-17",
    "2021-09-18",
    "2021-09-19",
    "2021-09-20",
    "2021-09-21",
    "2021-09-22",
    "2021-09-23",
    "2021-09-24",
    "2021-09-25",
    "2021-09-26",
    "2021-09-27",
    "2021-09-28",
    "2021-09-29",
    "2021-09-30",
    "2021-10-01",
    "2021-10-02",
    "2021-10-03",
    "2021-10-04",
    "2021-10-05",
    "2021-10-06",
    "2021-10-07",
    "2021-10-08",
    "2021-10-09",
    "2021-10-10",
    "2021-10-11",
    "2021-10-12",
    "2021-10-13",
    "2021-10-14",
    "2021-10-15",
    "2021-10-16",
    "2021-10-17",
    "2021-10-18",
    "2021-10-19",
    "2021-10-20",
    "2021-10-21",
    "2021-10-22",
    "2021-10-23",
    "2021-10-24",
    "2021-10-25",
    "2021-10-26",
    "2021-10-27",
    "2021-10-28",
    "2021-10-29",
    "2021-10-30",
    "2021-10-31",
    "2021-11-01",
    "2021-11-02",
    "2021-11-03",
    "2021-11-04",
    "2021-11-05",
    "2021-11-06",
    "2021-11-07",
    "2021-11-08",
    "2021-11-09",
    "2021-11-10",
    "2021-11-11",
    "2021-11-12",
    "2021-11-13",
    "2021-11-14",
    "2021-11-15",
    "2021-11-16",
    "2021-11-17",
    "2021-11-18",
    "2021-11-19",
    "2021-11-20",
    "2021-11-21",
    "2021-11-22",
    "2021-11-23",
    "2021-11-24",
    "2021-11-25",
    "2021-11-26",
    "2021-11-27",
    "2021-11-28",
    "2021-11-29",
    "2021-11-30",
    "2021-12-01",
    "2021-12-02",
    "2021-12-03",
    "2021-12-04",
    "2021-12-05",
    "2021-12-06",
    "2021-12-07",
    "2021-12-08",
    "2021-12-09",
    "2021-12-10",
    "2021-12-11",
    "2021-12-12",
    "2021-12-13",
    "2021-12-14",
    "2021-12-15",
    "2021-12-16",
    "2021-12-17",
    "2021-12-18",
    "2021-12-19",
    "2021-12-20",
    "2021-12-21",
    "2021-12-22",
    "2021-12-24",
    "2021-12-25",
    "2021-12-26",
    "2021-12-27"
];
const yArray = [
    42735.0946510772,
    44293.2880435401,
    44624.2544752479,
    45052.1269882587,
    43935.2796585518,
    44325.9091242991,
    44969.8413252551,
    45117.5157419979,
    44965.5297893367,
    43404.8357060411,
    43997.7969293693,
    43050.0825306712,
    43263.3742125568,
    44273.5627667132,
    42756.4669346249,
    42901.3690337721,
    42963.9121738051,
    43565.3240075335,
    42983.4348084438,
    44765.4141612179,
    43223.7684436099,
    43928.2087396455,
    43606.0637104269,
    43224.0573165165,
    42727.6270708664,
    44603.0417185291,
    42948.3949560346,
    44283.1602456677,
    43078.6205869154,
    44724.7261967555,
    43190.987836022,
    44580.738143223,
    44952.5822469736,
    44388.245310608,
    45043.3443895928,
    43287.2903022964,
    44580.9925238422,
    44529.5990156943,
    44257.3212109084,
    45035.721594089,
    43970.793779912,
    45138.4008219868,
    43208.9540061942,
    44273.8688857634,
    44880.3855780192,
    42938.2499120185,
    44436.8578780885,
    43804.0106359787,
    43643.2205269721,
    44790.7746154902,
    43613.7425558976,
    43420.8358158344,
    44357.4695672221,
    43396.9843991336,
    44664.8217167045,
    44626.6430661467,
    43633.4333404372,
    44879.3464978628,
    43415.308426787,
    43071.9118370263,
    43832.1994578135,
    45106.3704216487,
    45048.4363135125,
    45004.1525280941,
    43263.0724050425,
    44913.9595082161,
    44663.4377136747,
    44940.1564004566,
    45039.8046186038,
    44178.4718420318,
    44829.5223887892,
    45150.2273650111,
    43537.1610549142,
    43057.6923915673,
    42704.4137614815,
    42695.2646822626,
    43046.8876825556,
    43472.0137471864,
    43035.6259507367,
    43915.0456204865,
    44095.565317856,
    42849.6823411818,
    42916.4162941275,
    43593.0601180968,
    43903.861496314,
    42720.2155406226,
    44821.765935672,
    43899.3343835997,
    42841.1929269583,
    43085.5190443849,
    43841.8012483039,
    43389.3184882706,
    43174.4315380952,
    43298.5779033309,
    42934.8351755711,
    44649.6321756639,
    44055.3731800242,
    42938.754361721,
    44426.5489957075,
    44988.5447680694,
    43033.2423966233,
    43115.3591844765,
    45207.7369215409,
    44852.9824703006,
    43934.1125999699,
    45106.2739214133
];


/*
const xArrayMin = xArray.slice().sort()[0];
const xArrayMax = xArray.slice().sort().reverse()[0];
const yArrayMin = yArray.slice().sort(function(a, b){return a - b})[0];
const yArrayMax = yArray.slice().sort(function(a, b){return b - a})[0];
*//*

// Define Data
const data = [{
    x:xArray,
    y:yArray,
    mode:"lines"
}];

// Define Layout
const layout = {
    xaxis: {
        linecolor: '#636363',
        range: [xArrayMin, xArrayMax],
        type: 'date',
        title: "datum"},
    yaxis: {
        linecolor: '#636363',
        range: [yArrayMin * 0.95, yArrayMax * 1.05],
        title: "Koers"},
    title: "Bitcoin"
};*/

// TODO json als return value gebruiken
function getCryptoKoersInfo(cryptomunt) {
    fetch("/cryptoGrafiek", {
        method: 'POST',
        headers: {
            'Content-Type': 'text/plain',
        },
        body: cryptomunt,
    })
        .then((response) => {
            return response.json()
        })
        .then ((json) => {
            const xWaarden = getXArray(json);
            const yWaarden = getYArray(json);
            const xArrayMin = xWaarden.slice().sort()[0];
            const xArrayMax = xWaarden.slice().sort().reverse()[0];
            const yArrayMin = yWaarden.slice().sort(function(a, b){return a - b})[0];
            const yArrayMax = yWaarden.slice().sort(function(a, b){return b - a})[0];
            const data = [{
                x:xWaarden,
                y:yWaarden,
                mode:"lines"
            }];
            // Define Layout
            const layout = {
                xaxis: {
                    linecolor: '#636363',
                    range: [xArrayMin, xArrayMax],
                    type: 'date',
                    title: "datum"},
                yaxis: {
                    linecolor: '#636363',
                    range: [yArrayMin * 0.95, yArrayMax * 1.05],
                    title: "Koers"},
                title: cryptomunt
            };
            Plotly.newPlot(document.getElementById("myPlot"), data, layout);
        })
        .catch((error) => {
            console.error('*** Iets misgegaan:', error);
        });
}

const muntkiezer = document.getElementById("muntkiezer");

// Display using Plotly
//Plotly.newPlot(document.getElementById("myPlot"), data, layout);
const muntButton = document.getElementById("muntButton");
muntButton.addEventListener("click", () => {
    getCryptoKoersInfo(muntkiezer.value);
})
// dropdown met namen maken

// lijst cryptomunten binnenhalen
// loopen over lijst

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
    console.log(JSON.stringify(layOutNew));
    Plotly.newPlot(document.getElementById("myPlot"), data, layOutNew);
})

function range(element, dataArray){
    switch (element.textContent){
        case "Laatste week": return [dataArray[dataArray.length - 8], dataArray[dataArray.length - 1]];

        case "Laatste 30 dagen": return [dataArray[dataArray.length - 31], dataArray[dataArray.length - 1]];

    }
}

