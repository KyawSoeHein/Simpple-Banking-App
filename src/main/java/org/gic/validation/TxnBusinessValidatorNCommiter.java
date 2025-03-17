package org.gic.validation;

import org.gic.model.Account;
import org.gic.model.TransactionDetail;
import org.gic.singleton.AccountStorage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TxnBusinessValidatorNCommiter {
    private static void willAccountBalanceBecomeLowerThanZero(BigDecimal amountToDebit, BigDecimal accountBalance) throws IllegalArgumentException {
        if (accountBalance.subtract(amountToDebit).compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Transaction cannot be commited because of insufficient fund");
        }
    }

    private static void commit(Account account, TransactionDetail transactionDetail) throws IllegalArgumentException {
        switch (transactionDetail.transactionType()) {
            case DEPOSIT, INTEREST_EARNED -> account.addToBalance(transactionDetail.amount());
            case WITHDRAWAL -> {
                willAccountBalanceBecomeLowerThanZero(transactionDetail.amount(), account.getBalance());
                account.subtractFromBalance(transactionDetail.amount());
            }
            case null -> throw new IllegalArgumentException("Null transactionType, why does it happen?");
            default -> throw new IllegalArgumentException("Invalid transactionType");
        }

        List<TransactionDetail> transactionDetailList = account.getAccountStatementList().get(transactionDetail.transactionDate());
        if (transactionDetailList == null) {
            transactionDetailList = new ArrayList<>();
        }
        transactionDetailList.add(updateBalanceAfterTransactionValue(account.getBalance(), transactionDetail));
        account.getAccountStatementList().put(transactionDetail.transactionDate(), transactionDetailList);
        AccountStorage.getAccountStorage().put(account.getAccountNumber(), account);
    }

    private static TransactionDetail updateBalanceAfterTransactionValue(BigDecimal accountFinalBalance, TransactionDetail transactionDetail) {
        return new TransactionDetail(transactionDetail.transactionDate(),
                transactionDetail.accountNumber(), transactionDetail.transactionType(),
                transactionDetail.amount(), transactionDetail.transactionId(), transactionDetail.shortTransactionType(), accountFinalBalance);
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
