package controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CalculatorPersistence {
    private static final String FILE_PATH = "calculations.txt";

    public static void saveCalculation(double saldo, double risicoPercentage, double exchangeRate, double lotSize) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Formatteer de gegevens en schrijf deze naar het bestand
            String data = String.format("Datum: %s, Saldo: %.2f, Risicopercentage: %.2f, Wisselkoers: %.2f, Lotgrootte: %.2f%n",
                    now.format(formatter), saldo, risicoPercentage, exchangeRate, lotSize);
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
