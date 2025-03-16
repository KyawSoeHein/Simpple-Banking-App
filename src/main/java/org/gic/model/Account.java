package org.gic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class Account {
    private String accountNumber;
    private BigDecimal balance;
    private List<TransactionDetail> accountStatementList;
    private List<InterestRules> appliedInterestRules;

    public void addToBalance(BigDecimal balanceToAdd) {
        this.balance = this.balance.add(balanceToAdd);
    }

    public void subtractFromBalance(BigDecimal balanceToSubtract) {
        this.balance = this.balance.subtract(balanceToSubtract);
    }

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = BigDecimal.ZERO;
        this.accountStatementList = new ArrayList<>();
        this.appliedInterestRules = new ArrayList<>();
    }
}
