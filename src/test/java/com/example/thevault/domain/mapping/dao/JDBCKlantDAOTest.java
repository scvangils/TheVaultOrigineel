// Created by S.C. van Gils
// Creation date 20-12-2021

package com.example.thevault.domain.mapping.dao;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class JDBCKlantDAOTest {

    private JDBCKlantDAO jdbcKlantDAOTest;

    public JDBCKlantDAOTest(JDBCKlantDAO jdbcKlantDAO) {
        super();
        this.jdbcKlantDAOTest = jdbcKlantDAO;
    }

}
