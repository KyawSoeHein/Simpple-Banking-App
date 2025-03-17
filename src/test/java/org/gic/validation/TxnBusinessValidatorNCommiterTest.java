package org.gic.validation;

import org.gic.constants.DateConstants;
import org.gic.enums.TransactionType;
import org.gic.model.Account;
import org.gic.model.TransactionDetail;
import org.gic.singleton.AccountStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class TxnBusinessValidatorNCommiterTest {

    @BeforeEach
    void setUp() {
        AccountStorage.getAccountStorage().clear();
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
        TransactionDetail transactionDetail = new TransactionDetail(LocalDate.now(), "1111", TransactionType.DEPOSIT, new BigDecimal("100.00"), "1111-11", 'D', BigDecimal.ZERO);

        //Act & Assert
        assertDoesNotThrow(()-> TxnBusinessValidatorNCommiter.commitTransaction(transactionDetail));
        assertEquals(1, AccountStorage.getAccountStorage().size());
    }

    @Test
    void testAccountExists() {
        //Arrange
        TransactionDetail transactionDetail = new TransactionDetail(LocalDate.now(), "1111", TransactionType.DEPOSIT, new BigDecimal("100.00"), "1111-11", 'D', BigDecimal.ZERO);
        HashMap<String, Account> accountHashMap = AccountStorage.getAccountStorage();
        accountHashMap.put(transactionDetail.accountNumber(), new Account("1111", new BigDecimal("100.00"), new TreeMap<>()));

        //Act & Assert
        assertDoesNotThrow(()-> TxnBusinessValidatorNCommiter.commitTransaction(transactionDetail));
        assertEquals(1, AccountStorage.getAccountStorage().size());
    }

    @Test
    void testAccountWillGoLowerThanZero() {
        //Arrange
        TransactionDetail transactionDetail = new TransactionDetail(LocalDate.now(), "1111", TransactionType.WITHDRAWAL, new BigDecimal("100.00"), "1111-11", 'W', BigDecimal.ZERO);
        HashMap<String, Account> accountHashMap = AccountStorage.getAccountStorage();
        accountHashMap.put(transactionDetail.accountNumber(), new Account("1111", new BigDecimal("00.00"), new TreeMap<>()));

        //Act & Assert
        assertThrows(IllegalArgumentException.class, ()-> TxnBusinessValidatorNCommiter.commitTransaction(transactionDetail));
        assertEquals(1, AccountStorage.getAccountStorage().size());
        assertEquals(new BigDecimal("00.00"), accountHashMap.get(transactionDetail.accountNumber()).getBalance());
    }

    @Test
    void testTransactionWillCommit() {
        //Arrange
        TransactionDetail transactionDetail = new TransactionDetail(LocalDate.now(), "1111", TransactionType.DEPOSIT, new BigDecimal("50.00"), "1111-11", 'D', BigDecimal.ZERO);
        HashMap<String, Account> accountHashMap = AccountStorage.getAccountStorage();
        accountHashMap.put(transactionDetail.accountNumber(), new Account("1111", new BigDecimal("100.00"), new TreeMap<>()));

        //Act & Assert
        assertDoesNotThrow(()-> TxnBusinessValidatorNCommiter.commitTransaction(transactionDetail));
        assertEquals(1, AccountStorage.getAccountStorage().size());
        assertEquals(new BigDecimal("150.00"), accountHashMap.get(transactionDetail.accountNumber()).getBalance());
    }

    @Test
    void testTransactionWillCommitIfBalanceBecomesZero() {
        //Arrange
        TransactionDetail transactionDetail = new TransactionDetail(LocalDate.now(), "1111", TransactionType.WITHDRAWAL, new BigDecimal("100.00"), "1111-11", 'W', BigDecimal.ZERO);
        HashMap<String, Account> accountHashMap = AccountStorage.getAccountStorage();
        accountHashMap.put(transactionDetail.accountNumber(), new Account("1111", new BigDecimal("100.00"), new TreeMap<>()));

        //Act & Assert
        assertDoesNotThrow(()-> TxnBusinessValidatorNCommiter.commitTransaction(transactionDetail));
        assertEquals(1, AccountStorage.getAccountStorage().size());
        assertEquals(new BigDecimal("00.00"), accountHashMap.get(transactionDetail.accountNumber()).getBalance());
    }

    @Test
    void testAccountStatementIsNotUpdatedIfInvalidTypeIsPassed() {
        //Arrange
        TransactionDetail transactionDetail = new TransactionDetail(LocalDate.now(), "1111", null, new BigDecimal("100.00"), "1111-11", 'W', BigDecimal.ZERO);
        HashMap<String, Account> accountHashMap = AccountStorage.getAccountStorage();
        accountHashMap.put(transactionDetail.accountNumber(), new Account("1111", new BigDecimal("00.00"), new TreeMap<>()));

        //Act & Assert
        assertThrows(IllegalArgumentException.class, ()-> TxnBusinessValidatorNCommiter.commitTransaction(transactionDetail));
        assertEquals(1, AccountStorage.getAccountStorage().size());
        assertEquals(new BigDecimal("00.00"), accountHashMap.get(transactionDetail.accountNumber()).getBalance());
        assertEquals(0, AccountStorage.getAccountStorage().get("1111").getAccountStatementList().size());
    }

    @Test
    void testLowerThanZeroExceptionThrownForOldTransactionWithdrawal() {
        //Arrange
        TransactionDetail firstDeposit = new TransactionDetail(LocalDate.parse("20251230", DateConstants.TRANSACTION_DATE_FORMATTER), "12345", TransactionType.DEPOSIT, new BigDecimal("100.00"), "1111-11", 'D', BigDecimal.ZERO);
        TransactionDetail secondDeposit = new TransactionDetail(LocalDate.parse("20251231", DateConstants.TRANSACTION_DATE_FORMATTER), "12345", TransactionType.DEPOSIT, new BigDecimal("100.00"), "1111-11", 'D', BigDecimal.ZERO);
        TransactionDetail oldWithdrawal = new TransactionDetail(LocalDate.parse("20251120", DateConstants.TRANSACTION_DATE_FORMATTER), "12345", TransactionType.WITHDRAWAL, new BigDecimal("300.00"), "1111-11", 'W', BigDecimal.ZERO);

        //Act
        TxnBusinessValidatorNCommiter.commitTransaction(firstDeposit);
        TxnBusinessValidatorNCommiter.commitTransaction(secondDeposit);

        //Act & Assert
        assertEquals(2, AccountStorage.getAccountStorage().get("12345").getAccountStatementList().size());
        assertThrows(IllegalArgumentException.class, ()-> TxnBusinessValidatorNCommiter.commitTransaction(oldWithdrawal));
    }

    @Test
    void testMultipleTransactionsWithSameDateAreStored() {
        //Arrange
        TransactionDetail firstDeposit = new TransactionDetail(LocalDate.parse("20251230", DateConstants.TRANSACTION_DATE_FORMATTER), "12345", TransactionType.DEPOSIT, new BigDecimal("100.00"), "1111-11", 'D', BigDecimal.ZERO);
        TransactionDetail secondDeposit = new TransactionDetail(LocalDate.parse("20251230", DateConstants.TRANSACTION_DATE_FORMATTER), "12345", TransactionType.DEPOSIT, new BigDecimal("100.00"), "1111-12", 'D', BigDecimal.ZERO);
        TransactionDetail firstWithdrawal = new TransactionDetail(LocalDate.parse("20251230", DateConstants.TRANSACTION_DATE_FORMATTER), "12345", TransactionType.WITHDRAWAL, new BigDecimal("100.00"), "1111-13", 'W', BigDecimal.ZERO);

        //Act
        TxnBusinessValidatorNCommiter.commitTransaction(firstDeposit);
        TxnBusinessValidatorNCommiter.commitTransaction(secondDeposit);

        //Act & Assert
        assertDoesNotThrow(()-> TxnBusinessValidatorNCommiter.commitTransaction(firstWithdrawal));
        assertEquals(3, AccountStorage.getAccountStorage().get("12345").getAccountStatementList().get(LocalDate.parse("20251230", DateConstants.TRANSACTION_DATE_FORMATTER)).size());
    }

    @Test
    void testTransactionDetailsBalanceAfterTransactionIdUpdated() {
        //Arrange
        TransactionDetail firstDeposit = new TransactionDetail(LocalDate.parse("20251230", DateConstants.TRANSACTION_DATE_FORMATTER), "12345", TransactionType.DEPOSIT, new BigDecimal("100.00"), "1111-11", 'D', BigDecimal.ZERO);
        TransactionDetail secondDeposit = new TransactionDetail(LocalDate.parse("20251230", DateConstants.TRANSACTION_DATE_FORMATTER), "12345", TransactionType.DEPOSIT, new BigDecimal("100.00"), "1111-12", 'D', BigDecimal.ZERO);

        //Act
        TxnBusinessValidatorNCommiter.commitTransaction(firstDeposit);
        TxnBusinessValidatorNCommiter.commitTransaction(secondDeposit);

        //Assert
        Account account = AccountStorage.getAccountStorage().get("12345");
        List<TransactionDetail> transactionDetailList = account.getAccountStatementList().get(LocalDate.parse("20251230", DateConstants.TRANSACTION_DATE_FORMATTER));
        assertEquals(2, transactionDetailList.size());
        assertEquals(new BigDecimal("100.00"), transactionDetailList.get(0).balanceAfterTransaction());
        assertEquals(new BigDecimal("200.00"), transactionDetailList.get(1).balanceAfterTransaction());
    }
}