package questions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class QuestionLoader {
    public static List<Question> loadQuestions(String filename) {
        List<Question> questions = new ArrayList<>();
        String resourcePath = filename.startsWith("/") ? filename : "/" + filename;
        try (InputStream is = QuestionLoader.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                System.err.println("Critical Error: Unable to find " + filename + " in the resources folder.");
                return questions;
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(";");
                    if (parts.length == 2) {
                        questions.add(new Question(parts[0].trim(), parts[1].trim()));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading questions: " + e.getMessage());
        }

        return questions;
    }
}