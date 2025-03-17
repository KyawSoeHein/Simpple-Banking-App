package org.gic.validation;

import org.gic.enums.TransactionType;
import org.gic.model.TransactionDetail;
import org.gic.singleton.TransactionIdGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.gic.constants.DateConstants.TRANSACTION_DATE_FORMATTER;

public class InputTxnFmtValidatorNTransformer {
    private static final String transactionDetailInputCheckRegex = "^(\\S+)\\s(\\S+)\\s(\\S+)\\s(\\S+)$";
    private static final Pattern transactionDetailInputCheckPattern = Pattern.compile(transactionDetailInputCheckRegex);

    private static LocalDate isDateFormatValid(String transactionDate) throws DateTimeParseException {
        try {
            return LocalDate.parse(transactionDate, TRANSACTION_DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Date needs to be in valid range and YYYYMMdd format", transactionDate, 0);
        }
    }

    private static BigDecimal isAmountValid(String amountStr) throws IllegalArgumentException {
        BigDecimal amount = new BigDecimal(amountStr);
        if (amount.compareTo(BigDecimal.ZERO) < 0 || amount.scale() > 2) {
            throw new IllegalArgumentException("Amount must be greater than zero and have no more than 2 decimal places");
        }

        return amount;
    }

    private static TransactionType isValidTransactionType(String transactionType) throws IllegalArgumentException {
        return TransactionType.fromCode(transactionType);
    }

    private static String generateRandomTransactionId(String transactionDate, TransactionType transactionType) {
        return transactionType.equals(TransactionType.WITHDRAWAL)
                ? transactionDate + "-" +TransactionIdGenerator.getNextWithdrawTransactionId()
                : transactionDate + "-" +TransactionIdGenerator.getNextDepositTransactionId();
    }

    private static void validateInputFormat(Matcher matcher) throws Exception {
        if (!matcher.matches()) {
            throw new Exception("TransactionDetailDTOTransformer : Invalid input format. Please input with <Date> <Account> <Type> <Amount> format");
        }
    }

    public static TransactionDetail validateAndTransform(String input) throws Exception {
        Matcher matcher = transactionDetailInputCheckPattern.matcher(input);
        validateInputFormat(matcher);

        LocalDate transactionDate = isDateFormatValid(matcher.group(1));
        String accountNumber = matcher.group(2);
        char shortTransactionType = matcher.group(3).charAt(0);
        TransactionType transactionType = isValidTransactionType(matcher.group(3));
        String transactionId = generateRandomTransactionId(matcher.group(1), transactionType);
        BigDecimal amount = isAmountValid(matcher.group(4));

        return new TransactionDetail(transactionDate, accountNumber, transactionType, amount, transactionId, shortTransactionType, BigDecimal.ZERO);
    }
}
