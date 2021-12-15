// Created by carme
// Creation date 15/12/2021

package com.example.thevault.domain.mapping.dao;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class JDBCTestDAOTest {

    @Test
    public void setupTest(){
        assertThat(1).isNotNull();
    }
}
