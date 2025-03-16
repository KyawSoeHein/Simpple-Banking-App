package org.gic.validation;

import org.gic.model.Account;
import org.gic.model.TransactionDetail;
import org.gic.singleton.AccountStorage;

import java.math.BigDecimal;

public class TxnBusinessValidatorNCommiter {
    private static void willAccountBalanceBecomeLowerThanZero(BigDecimal amountToDebit, BigDecimal accountBalance) throws IllegalArgumentException {
        if (accountBalance.subtract(amountToDebit).compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Transaction cannot be commited because of insufficient fund");
        }
    }

    private static void commit(Account account, TransactionDetail transactionDetail) throws IllegalArgumentException {
        switch (transactionDetail.transactionType()) {
            case DEPOSIT -> account.addToBalance(transactionDetail.amount());
            case WITHDRAWAL -> {
                willAccountBalanceBecomeLowerThanZero(transactionDetail.amount(), account.getBalance());
                account.subtractFromBalance(transactionDetail.amount());
            }
            case INTEREST_EARNED -> throw new IllegalArgumentException("You are not allowed to generate interest");
            case null -> throw new IllegalArgumentException("Null transactionType, why does it happen?");
            default -> throw new IllegalArgumentException("Invalid transactionType");
        }

        account.getAccountStatementList().add(transactionDetail);
        AccountStorage.getAccountStorage().put(account.getAccountNumber(), account);
    }

    private static Account addAccount(Account account) throws RuntimeException {
        AccountStorage.getAccountStorage().put(account.getAccountNumber(), account);
        return account;
    }

    private static Account getOrCreateAccount(String accountNumber) throws IllegalArgumentException {
        Account account = AccountStorage.getAccountStorage().get(accountNumber);
        if (account == null) {
            account = addAccount(new Account(accountNumber));
        }

        return account;
    }

    public static Account commitTransaction(TransactionDetail transactionDetail) throws IllegalArgumentException {
        if (transactionDetail == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        Account account = getOrCreateAccount(transactionDetail.accountNumber());
        commit(account, transactionDetail);

        return account;
    }
}
