package org.gic.constants;

import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class DateConstants {
    public static DateTimeFormatter TRANSACTION_DATE_FORMATTER = DateTimeFormatter.ofPattern("uuuuMMdd").withResolverStyle(ResolverStyle.STRICT);
    public static DateTimeFormatter INTEREST_RULE_DATE_FORMATTER = DateTimeFormatter.ofPattern("uuuuMMdd").withResolverStyle(ResolverStyle.STRICT);


}
