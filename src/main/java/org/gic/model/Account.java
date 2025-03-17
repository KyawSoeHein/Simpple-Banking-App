package org.gic.model;

import com.sun.source.tree.Tree;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

@AllArgsConstructor
@Getter
public class Account {
    private String accountNumber;
    private BigDecimal balance;
    private TreeMap<LocalDate, TransactionDetail> accountStatementList;

    public void addToBalance(BigDecimal balanceToAdd) {
        this.balance = this.balance.add(balanceToAdd);
    }

    public void subtractFromBalance(BigDecimal balanceToSubtract) {
        this.balance = this.balance.subtract(balanceToSubtract);
    }

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = BigDecimal.ZERO;
        this.accountStatementList = new TreeMap<>();
    }
}
