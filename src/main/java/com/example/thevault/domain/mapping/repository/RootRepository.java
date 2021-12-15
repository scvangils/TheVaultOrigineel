// Created by carme
// Creation date 01/12/2021

package com.example.thevault.domain.mapping.repository;

import com.example.thevault.domain.mapping.dao.*;
import com.example.thevault.domain.model.*;
import com.example.thevault.support.exceptions.AssetNotExistsException;
import net.minidev.json.annotate.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class RootRepository {

    @JsonIgnore
    private final Logger logger = LoggerFactory.getLogger(RootRepository.class);

    private final KlantDAO klantDAO;
    private final RekeningDAO rekeningDAO;
    private final AssetDAO assetDAO;
    private final CryptomuntDAO cryptomuntDAO;
    private final CryptoWaardeDAO cryptoWaardeDAO;
    private final TransactieDAO transactieDAO;

    @Autowired
    public RootRepository(KlantDAO klantDAO, RekeningDAO rekeningDAO, AssetDAO assetDAO, CryptomuntDAO cryptomuntDAO,
                          CryptoWaardeDAO cryptoWaardeDAO, TransactieDAO transactieDAO) {
        super();
        this.rekeningDAO = rekeningDAO;
        this.klantDAO = klantDAO;
        this.assetDAO = assetDAO;
        this.cryptomuntDAO = cryptomuntDAO;
        this.cryptoWaardeDAO = cryptoWaardeDAO;
        this.transactieDAO = transactieDAO;
        logger.info("New RootRepository");
    }

    /**
     * Deze methode slaat de gegevens van een klant op in de database
     * op basis van een klant-object, de gebruikerId gaat van 0 naar de juiste
     *
     * @param klant het klant-object op basis van bij registratie ingevoerde gegevens
     * @return het klant-object met de juiste gebruikerId
     */
    public Klant slaKlantOp(Klant klant){
        return klantDAO.slaKlantOp(klant);
    }

    /**
     * Deze methode zoekt of er in de database al een klant bestaat met deze gebruikersnaam
     * en maakt eventueel een klant-object aan op nasis van de teruggestuurde gegevens
     * Hier in de repository worden portefeuille en rekening toegevoegd
     *
     * @param gebruikersnaam gebruikersnaam van een (mogelijke) klant die uniek moet zijn
     * @return klant-object op basis van gegevens uit de database of null indien gebruikersnaam niet gevonden is
     */
    public Klant vindKlantByGebruikersnaam(String gebruikersnaam){
        Klant klant = klantDAO.vindKlantByGebruikersnaam(gebruikersnaam);
            klant.setPortefeuille(vulPortefeuilleKlant(klant));
            //TODO rekening toevoegen na database compleet
        return klant;
    }

    /**
     * Deze methode zoekt of er in de database al een klant bestaat met deze gebruikerId
     * en maakt eventueel een klant-object aan op nasis van de teruggestuurde gegevens
     *
     * @param gebruikerId gebruikerId van een (mogelijke) klant die uniek moet zijn
     * @return klant-object op basis van gegevens uit de database of null indien gebruikerId niet gevonden is
     */
    public Klant vindKlantById(int gebruikerId){
        return klantDAO.vindKlantById(gebruikerId);
    }

    /**
     * Deze methode slaat de gegevens van een rekening op in de database via de methode in de rekeningDAO.
     *
     * @param rekening is de rekening die is aangemaakt bij een nieuwe klant.
     * @return de rekening behorend bij de nieuwe klant met klant-id
     */
    public Rekening slaRekeningOp(Rekening rekening){
        return rekeningDAO.slaRekeningOp(rekening);
    }

    /**
     * Deze methode vindt de rekening van klant in de database via de methode in de rekeningDAO.
     *
     * @param klant is de klant van wie de rekening wordt opgevraagd.
     * @return de rekening behorend bij de klant met klant-id
     */
    public Rekening vindRekeningVanKlant(Klant klant){
        return rekeningDAO.vindRekeningVanKlant(klant);
    }

    /**
     * Deze methode geeft het rekeningsaldo op van de klant in de database via de methode in de rekeningDAO.
     *
     * @param klant is de klant van wie het rekeningsaldo wordt opgevraagd.
     * @return het rekeningsaldo behorend bij de klant met klant-id
     */
    public double vraagSaldoOpVanKlant(Klant klant){
        return rekeningDAO.vraagSaldoOpVanKlant(klant);
    }

    /**
     * Deze methode wijzigt het rekeningsaldo van de klant in de database via de methode in de rekeningDAO.
     *
     * @param klant is de klant van wie het rekeningsaldo wordt opgevraagd.
     * @param bedrag is het bedrag waarnaar het saldo gewijzigd moet worden.
     * @return het rekeningsaldo behorend bij de klant met klant-id wordt gewijzigd.
     */
    public Rekening wijzigSaldoVanKlant(Klant klant, double bedrag){
        return rekeningDAO.wijzigSaldoVanKlant(klant, bedrag);
    }

    /**
     * @Author Carmen
     * Dit betreft het vullen van de portefeuille met alle cryptomunten die er in zitten. Voor iedere asset
     * wordt alle informatie over de bijbehorende cryptomunt opgevraagd en meegegeven
     * @param klant de klant die informatie opvraagt over de cryptomunt
     * @return List</Asset> een lijst van alle Assets (cryptomunten + hoeveelheden) in het bezit van de klant
     */
    public List<Asset> vulPortefeuilleKlant(Klant klant){
        List<Asset> portefeuille = assetDAO.geefAlleAssets(klant);
        for (Asset asset: portefeuille) {
            asset.setCryptomunt(cryptomuntDAO.geefCryptomunt(asset.getCryptomunt().getId()));
        }
        return portefeuille;
    }

    /**
     * Dit betreft het vinden van een specifieke cryptomunt die in de portefeuille zit
     * @param klant de klant die informatie opvraagt over de cryptomunt
     * @param cryptomunt cryptomunt waarover informatie wordt opgevraagd
     * @return Asset de asset (cryptomunt + aantal) waarover informatie is opgevraagd
     */
    public Asset geefAssetVanKlant(Klant klant, Cryptomunt cryptomunt){
        return assetDAO.geefAsset(klant, cryptomunt).orElseThrow(AssetNotExistsException::new);
    }

    /**
     * Dit betreft het toevoegen van een cryptomunt die nog niet in de portefeuille zit
     * Dit gebeurt via een 'transactie', waarbij een klant crypto's koopt
     * @param asset de cryptomunt en het aantal dat de klant aanschaft
     * @return Asset de asset die de klant heeft toegevoegd
     */
    public Asset slaNieuwAssetVanKlantOp(Asset asset){
        return assetDAO.voegNieuwAssetToeAanPortefeuille(asset);
    }

    /**
     * Dit betreft het wijzigen van een cryptomunt die al in de portefeuille zit
     * Dit gebeurt via een 'transactie', waarbij een klant crypto's koopt of verkoopt
     * @param asset de asset waarin de klant handelt, met de informatie wélke cryptomunt wordt verhandeld
     *              en hoeveel deze omhoog/omlaag gaat (oftewel: betreft het een koop of een verkoop)
     * @return Asset de asset na de update, waarbij het nieuwe aantal wordt meegegeven
     */
    public Asset wijzigAssetVanKlant(Asset asset){
        return assetDAO.updateAsset(asset);
    }

    //CryptoWaarde wordt eens per dag opgehaald om 00.00 uur
    public CryptoWaarde haalMeestRecenteCryptoWaarde(Cryptomunt cryptomunt){
        return cryptoWaardeDAO.getCryptoWaardeByCryptomuntAndDate(cryptomunt, LocalDate.now());
    }

    /**
     * Sla een transactie op in de transactie database tabel
     * @param transactie de transactie die in de database moet worden toegevoegd
     * @return transactie die is opgeslagen
     */
    public Transactie SlaTransactieOp(Transactie transactie){
        return transactieDAO.slaTransactieOp(transactie);
    }

    /**
     * methode die alle transacties die bij een klant horen teruggeeft
     * @param klant de klant waarvan alle transacties moeten worden opgezocht
     * @return lijst transacties van de klant
     */
    List<Transactie> geefTransactiesVanKlant(Klant klant){
        return transactieDAO.geefTransactiesVanKlant(klant);
    }
    /**
     * methode die alle transacties van de bank ophaalt
     *
     * @return lijst transacties van de bank
     */
    List<Transactie> geefTransactiesVanBank(){
        return transactieDAO.geefTransactiesVanBank();
    }

    /**
     * methode die alle transacties die bij een klant horen die in een bepaalde
     * periode hebben plaatsgevonden teruggeeft
     * @params klant, startDatum en eindDatum de klant waarvan alle transacties moeten
     * worden opgezocht, en data vanaf en tot wanneer de transacties plaatsvonden
     * @return lijst transacties van de klant
     */
    List<Transactie> geefTransactiesVanKlantInPeriode(Klant klant, LocalDateTime startDatum, LocalDateTime eindDatum){
        return transactieDAO.geefTransactiesVanKlantInPeriode(klant, startDatum, eindDatum);
    }

    /**
     * methode die alle transacties binnen een bepaalde periode teruggeeft
     * @params startDatum en eindDatum periode
     * @return lijst transacties van die periode
     */
    List<Transactie> geefAlleTransactiesInPeriode(LocalDateTime startDatum, LocalDateTime eindDatum){
        return transactieDAO.geefAlleTransactiesInPeriode(startDatum, eindDatum);
    }

    /**
     * methode die alle transacties die bij een klant horen met een bepaalde cryptmunt
     * @params klant cryptomunt
     * @return lijst transacties van de klant met de meegegeven cryptomunt
     */
    List<Transactie> geefTransactiesVanKlantMetCryptomunt(Klant klant, Cryptomunt cryptomunt){
        return transactieDAO.geefTransactiesVanKlantMetCryptomunt(klant, cryptomunt);
    }


}
