package com;

import java.text.DateFormatSymbols;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Currency;
import java.util.Locale;
import java.util.ResourceBundle;

public class Info {
    public void execute(Locale targetLocale, ResourceBundle messages) {
        String pattern = messages.getString("info");
        System.out.println(MessageFormat.format(pattern, targetLocale.getDisplayName(targetLocale)));

        System.out.println("\tCountry: " + targetLocale.getDisplayCountry(Locale.ENGLISH)
                + " (" + targetLocale.getDisplayCountry(targetLocale) + ")");

        System.out.println("\tLanguage: " + targetLocale.getDisplayLanguage(Locale.ENGLISH)
                + " (" + targetLocale.getDisplayLanguage(targetLocale) + ")");

        try {
            Currency currency = Currency.getInstance(targetLocale);
            if (currency != null) {
                System.out.println("\tCurrency: " + currency.getCurrencyCode()
                        + " (" + currency.getDisplayName(targetLocale) + ")");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("\tCurrency: Not available for this specific locale tag.");
        }

        DateFormatSymbols dfs = DateFormatSymbols.getInstance(targetLocale);

        String[] weekdays = dfs.getWeekdays();
        StringBuilder wd = new StringBuilder("\tWeek Days: ");
        for (int i = 1; i < weekdays.length; i++) {
            if (!weekdays[i].isEmpty()) {
                wd.append(weekdays[i]).append(", ");
            }
        }
        if (wd.length() > 2) wd.setLength(wd.length() - 2);
        System.out.println(wd.toString());

        String[] months = dfs.getMonths();
        StringBuilder ms = new StringBuilder("\tMonths: ");
        for (String month : months) {
            if (!month.isEmpty()) {
                ms.append(month).append(", ");
            }
        }
        if (ms.length() > 2) ms.setLength(ms.length() - 2);
        System.out.println(ms.toString());

        // Today's Date Formatting
        LocalDate today = LocalDate.now();
        DateTimeFormatter defaultFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(Locale.ENGLISH);
        DateTimeFormatter localeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(targetLocale);

        System.out.println("\tToday: " + today.format(defaultFormatter)
                + " (" + today.format(localeFormatter) + ")");
    }
}