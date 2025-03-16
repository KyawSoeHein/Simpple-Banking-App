package org.gic.validation;

import org.gic.enums.TransactionType;
import org.gic.model.Account;
import org.gic.model.TransactionDetail;
import org.gic.singleton.AccountStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TxnBusinessValidatorNCommiterTest {

    private MockedStatic<AccountStorage> accountStorageMockedStatic;

    @BeforeEach
    void setUp() {
        accountStorageMockedStatic = Mockito.mockStatic(AccountStorage.class);
    }

    @AfterEach
    void tearDown() {
        accountStorageMockedStatic.close();
    }

    @Test
    void testTransactionDetailIsNull() {
        //Arrange
        TransactionDetail transactionDetail = null;

        //Act & Assert
        assertThrows(IllegalArgumentException.class, ()-> TxnBusinessValidatorNCommiter.commitTransaction(transactionDetail));
    }

    @Test
    void testAccountDoesNotExist() {
        //Arrange
        TransactionDetail transactionDetail = new TransactionDetail(LocalDate.now(), "1111", TransactionType.DEPOSIT, new BigDecimal("100.00"), "1111-11", 'D');
        accountStorageMockedStatic.when(AccountStorage::getAccountStorage).thenReturn(new HashMap<>());

        //Act & Assert
        assertDoesNotThrow(()-> TxnBusinessValidatorNCommiter.commitTransaction(transactionDetail));
        assertEquals(1, AccountStorage.getAccountStorage().size());
    }

    @Test
    void testAccountExists() {
        //Arrange
        TransactionDetail transactionDetail = new TransactionDetail(LocalDate.now(), "1111", TransactionType.DEPOSIT, new BigDecimal("100.00"), "1111-11", 'D');
        accountStorageMockedStatic.when(AccountStorage::getAccountStorage).thenReturn(new HashMap<>());
        HashMap<String, Account> accountHashMap = AccountStorage.getAccountStorage();
        accountHashMap.put(transactionDetail.accountNumber(), new Account("1111", new BigDecimal("100.00"), new ArrayList<>(), new ArrayList<>()));

        //Act & Assert
        assertDoesNotThrow(()-> TxnBusinessValidatorNCommiter.commitTransaction(transactionDetail));
        assertEquals(1, AccountStorage.getAccountStorage().size());
    }

    @Test
    void testAccountWillGoLowerThanZero() {
        //Arrange
        TransactionDetail transactionDetail = new TransactionDetail(LocalDate.now(), "1111", TransactionType.WITHDRAWAL, new BigDecimal("100.00"), "1111-11", 'W');
        accountStorageMockedStatic.when(AccountStorage::getAccountStorage).thenReturn(new HashMap<>());
        HashMap<String, Account> accountHashMap = AccountStorage.getAccountStorage();
        accountHashMap.put(transactionDetail.accountNumber(), new Account("1111", new BigDecimal("00.00"), new ArrayList<>(), new ArrayList<>()));

        //Act & Assert
        assertThrows(IllegalArgumentException.class, ()-> TxnBusinessValidatorNCommiter.commitTransaction(transactionDetail));
        assertEquals(1, AccountStorage.getAccountStorage().size());
        assertEquals(new BigDecimal("00.00"), accountHashMap.get(transactionDetail.accountNumber()).getBalance());
    }

    @Test
    void testTransactionWillCommit() {
        //Arrange
        TransactionDetail transactionDetail = new TransactionDetail(LocalDate.now(), "1111", TransactionType.DEPOSIT, new BigDecimal("50.00"), "1111-11", 'D');
        accountStorageMockedStatic.when(AccountStorage::getAccountStorage).thenReturn(new HashMap<>());
        HashMap<String, Account> accountHashMap = AccountStorage.getAccountStorage();
        accountHashMap.put(transactionDetail.accountNumber(), new Account("1111", new BigDecimal("100.00"), new ArrayList<>(), new ArrayList<>()));

        //Act & Assert
        assertDoesNotThrow(()-> TxnBusinessValidatorNCommiter.commitTransaction(transactionDetail));
        assertEquals(1, AccountStorage.getAccountStorage().size());
        assertEquals(new BigDecimal("150.00"), accountHashMap.get(transactionDetail.accountNumber()).getBalance());
    }

    @Test
    void testTransactionWillCommitIfBalanceBecomesZero() {
        //Arrange
        TransactionDetail transactionDetail = new TransactionDetail(LocalDate.now(), "1111", TransactionType.WITHDRAWAL, new BigDecimal("100.00"), "1111-11", 'W');
        accountStorageMockedStatic.when(AccountStorage::getAccountStorage).thenReturn(new HashMap<>());
        HashMap<String, Account> accountHashMap = AccountStorage.getAccountStorage();
        accountHashMap.put(transactionDetail.accountNumber(), new Account("1111", new BigDecimal("100.00"), new ArrayList<>(), new ArrayList<>()));

        //Act & Assert
        assertDoesNotThrow(()-> TxnBusinessValidatorNCommiter.commitTransaction(transactionDetail));
        assertEquals(1, AccountStorage.getAccountStorage().size());
        assertEquals(new BigDecimal("00.00"), accountHashMap.get(transactionDetail.accountNumber()).getBalance());
    }

    @Test
    void testAccountStatementIsNotUpdatedIfInvalidTypeIsPassed() {
        //Arrange
        TransactionDetail transactionDetail = new TransactionDetail(LocalDate.now(), "1111", null, new BigDecimal("100.00"), "1111-11", 'W');
        accountStorageMockedStatic.when(AccountStorage::getAccountStorage).thenReturn(new HashMap<>());
        HashMap<String, Account> accountHashMap = AccountStorage.getAccountStorage();
        accountHashMap.put(transactionDetail.accountNumber(), new Account("1111", new BigDecimal("00.00"), new ArrayList<>(), new ArrayList<>()));

        //Act & Assert
        assertThrows(IllegalArgumentException.class, ()-> TxnBusinessValidatorNCommiter.commitTransaction(transactionDetail));
        assertEquals(1, AccountStorage.getAccountStorage().size());
        assertEquals(new BigDecimal("00.00"), accountHashMap.get(transactionDetail.accountNumber()).getBalance());
        assertEquals(0, AccountStorage.getAccountStorage().get("1111").getAccountStatementList().size());
    }
}