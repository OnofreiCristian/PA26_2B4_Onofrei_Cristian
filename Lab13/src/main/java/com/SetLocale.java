package com;

import app.LocaleExplore;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class SetLocale {
    public void execute(LocaleExplore app, Locale newLocale) {
        app.setLocale(newLocale);
        ResourceBundle updatedMessages = app.getMessages();
        String pattern = updatedMessages.getString("locale.set");

        System.out.println(MessageFormat.format(pattern, newLocale.toLanguageTag()));
    }
}