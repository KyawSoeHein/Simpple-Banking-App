package org.gic.ui.components;

import org.gic.model.TransactionDetail;
import org.gic.singleton.TextScanner;
import org.gic.validation.InputTxnFmtValidatorNTransformer;
import org.gic.validation.TxnBusinessValidatorNCommiter;

public class InputTransactionDetailReader {
    public static void readTransactionDetails() throws Exception {
        System.out.print("Enter your transaction details: ");
        String transactionDetailStr = TextScanner.getScanner().nextLine();
        TransactionDetail transactionDetail = InputTxnFmtValidatorNTransformer.validateAndTransform(transactionDetailStr);
        TxnBusinessValidatorNCommiter.commitTransaction(transactionDetail);
    }
}
