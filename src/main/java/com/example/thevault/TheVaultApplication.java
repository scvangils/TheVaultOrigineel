package com.example.thevault;

import com.example.thevault.domain.model.Cryptomunt;
import com.example.thevault.domain.model.Klant;
import com.example.thevault.domain.model.Transactie;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class TheVaultApplication {

    public static void main(String[] args) {
        SpringApplication.run(TheVaultApplication.class, args);

        Transactie transactie = new Transactie(1, 2, LocalDateTime.now(),
                new Klant(), new Cryptomunt(1), 1111,
        new Klant());

    }

}
