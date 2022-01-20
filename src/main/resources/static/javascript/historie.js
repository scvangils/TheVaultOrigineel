async function openKlantHistorie(jwt, gebruikersnaam) {
    await validateJWT(jwt, gebruikersnaam);
}