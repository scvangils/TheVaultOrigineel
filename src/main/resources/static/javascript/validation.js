function validateJWT(jwtString, gebruikersnaam){
    console.log(gebruikersnaam);
    const endpoint = "/validate/jwt";
    fetch(endpoint, {
        method: 'POST',
    headers: {
        'Authorization': 'Bearer' + jwtString,
    },
    body: gebruikersnaam,
    }).then((response) => {
        if(response.status === 200){
            showDashboard()
        }
        if(response.status === 401){
            validateRefresh()
        }
        else console.log("*** andere status: " + response.statusText)
    }).catch((error) => {
        console.error('*** Iets misgegaan:', error);
    })
}

function getJWT(response){
    console.log("*** moet de jwt uit de header lezen ***")
    return response.headers.get("Authorization").substr(7); // verwijder "Bearer "

}
function validateRefresh(gebruikersnaam){
    console.log("*** jwt niet geldig ***")
    const endpoint = "/validate/refresh"
    fetch(endpoint, {
        method: 'POST',
        body: gebruikersnaam
    }).then((response) => {
        if(response.status === 200){
            const jwt = response.headers.get("Authorization").substr(7); // verwijder "Bearer "
            console.log(jwt);
            return jwt;
        } //TODO status bepalen
        if(response.status === 400){
            console.log(response.statusText);
            console.log("*** refresh token niet meer geldig ***");
            showLogin();
        }
        else console.log("*** andere status: " + response.statusText)
    }).catch((error) => {
        console.error('*** Iets misgegaan:', error);
    })
}
function showDashboard(){
    console.log("*** jwt gevalideerd ***")
}
function showLogin(){
    console.log("*** terug naar het login-scherm ***")
}
