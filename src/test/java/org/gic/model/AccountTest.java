package org.gic.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void addToBalance() {
        //Arrange
        Account account = new Account("11111", new BigDecimal("100"), new ArrayList<>(), new ArrayList<>());

        //Act
        account.addToBalance(new BigDecimal("100"));

        //Assert
        assertEquals(new BigDecimal("200"), account.getBalance());
    }

    @Test
    void subtractFromBalance() {
        //Arrange
        Account account = new Account("11111", new BigDecimal("100"), new ArrayList<>(), new ArrayList<>());

        //Act
        account.subtractFromBalance(new BigDecimal("100"));

        //Assert
        assertEquals(new BigDecimal("00"), account.getBalance());
    }
}