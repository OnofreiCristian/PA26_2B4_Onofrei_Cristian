package com;

import java.util.Locale;
import java.util.ResourceBundle;

public class DisplayLocales {
    public void execute(ResourceBundle messages) {
        System.out.println(messages.getString("locales"));
        Locale[] available = Locale.getAvailableLocales();
        for (Locale loc : available) {
            System.out.println(loc.toLanguageTag() + " - " + loc.getDisplayName());
        }
    }
}