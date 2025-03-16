package org.gic.validation;

import org.gic.model.Account;
import org.gic.model.TransactionDetail;
import org.gic.singleton.AccountStorage;

import java.math.BigDecimal;

public class TransactionValidatorAndCommiter {
    private static void willAccountBalanceBecomeLowerThanZero(BigDecimal amountToDebit, BigDecimal accountBalance) throws IllegalArgumentException {
        if (accountBalance.subtract(amountToDebit).compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Transaction cannot be commited because of insufficient funds");
        }
    }

    private static void commit(Account account, TransactionDetail transactionDetail) {
        account.getAccountStatementList().add(transactionDetail);
        account.subtractFromBalance(transactionDetail.amount());
        AccountStorage.getAccountStorage().put(account.getAccountNumber(), account);
    }

    private static Account checkIfAccountExists(String accountNumber) throws IllegalArgumentException {
        Account account = AccountStorage.getAccountStorage().get(accountNumber);
        if (account == null) {
            throw new IllegalArgumentException("Account does not exist");
        }

        return account;
    }

    public static void commitTransaction(TransactionDetail transactionDetail) throws IllegalArgumentException {
        if (transactionDetail == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        Account account = checkIfAccountExists(transactionDetail.accountNumber());
        willAccountBalanceBecomeLowerThanZero(transactionDetail.amount(), account.getBalance());
        commit(account, transactionDetail);
    }
}
