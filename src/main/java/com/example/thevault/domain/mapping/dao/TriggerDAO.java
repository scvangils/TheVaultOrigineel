package com.example.thevault.domain.mapping.dao;

import com.example.thevault.domain.model.Gebruiker;
import com.example.thevault.domain.model.Trigger;

import java.util.List;

public interface TriggerDAO {

    Trigger slaTriggerOp(Trigger trigger);

    int verwijderTrigger(Trigger trigger);

    List<Trigger> vindTriggersByGebruiker(Gebruiker gebruiker, String type);

    List<Trigger> vindAlleTriggers(String koperOfVerkoper);

    List<Trigger> vindTriggersByAantalCryptomuntEnPrijs(Trigger trigger);

}
