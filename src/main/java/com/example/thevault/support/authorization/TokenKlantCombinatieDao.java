/*
 * @Author Elise Olthof
 * 12-07-2021
 * */

package com.example.thevault.support.authorization;

import com.example.thevault.domain.model.Klant;


import java.util.Optional;
import java.util.UUID;

public interface TokenKlantCombinatieDao {

    public void slaTokenKlantPairOp(TokenKlantCombinatie tokenKlantCombinatie);

    public Optional<TokenKlantCombinatie> vindTokenKlantPairMetKey (UUID key);

    public Optional<TokenKlantCombinatie> vindTokenKlantPairMetKlant(Klant klant);

    public void delete(UUID uuid);
}
