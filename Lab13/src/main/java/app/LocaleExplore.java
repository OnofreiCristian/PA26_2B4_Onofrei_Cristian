package app;

import com.DisplayLocales;
import com.Info;
import com.SetLocale;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class LocaleExplore {
    private static final String BASE_NAME = "res.Messages";
    private Locale currentLocale;
    private ResourceBundle messages;

    public LocaleExplore() {
        setLocale(Locale.getDefault());
    }

    public void setLocale(Locale locale) {
        this.currentLocale = locale;
        this.messages = ResourceBundle.getBundle(BASE_NAME, locale);
    }

    public ResourceBundle getMessages() {
        return this.messages;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(messages.getString("prompt") + " ");
            String commandLine = scanner.nextLine().trim();

            if (commandLine.equalsIgnoreCase("exit") || commandLine.equalsIgnoreCase("quit")) {
                break;
            }

            String[] tokens = commandLine.split("\\s+");
            String command = tokens[0].toLowerCase();

            switch (command) {
                case "locales":
                    new DisplayLocales().execute(messages);
                    break;
                case "set":
                    if (tokens.length > 1) {
                        // Locale.forLanguageTag expects hyphens (e.g., ro-RO)
                        String tag = tokens[1].replace('_', '-');
                        new SetLocale().execute(this, Locale.forLanguageTag(tag));
                    } else {
                        System.out.println(messages.getString("invalid"));
                    }
                    break;
                case "info":
                    if (tokens.length > 1) {
                        String tag = tokens[1].replace('_', '-');
                        new Info().execute(Locale.forLanguageTag(tag), messages);
                    } else {
                        new Info().execute(this.currentLocale, messages);
                    }
                    break;
                default:
                    System.out.println(messages.getString("invalid"));
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        new LocaleExplore().run();
    }
}