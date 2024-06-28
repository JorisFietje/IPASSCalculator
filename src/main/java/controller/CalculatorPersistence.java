package controller;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CalculatorPersistence {
    private static final String FILE_PATH = "calculations.txt";

    public static void saveCalculation(String username, double saldo, double risicoPercentage, double exchangeRate, double lotSize) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Format the data and write it to the file
            String data = String.format("Username: %s, Date: %s, Balance: %.2f, Risk Percentage: %.2f, Exchange Rate: %.2f, Lot Size: %.2f%n",
                    username, now.format(formatter), saldo, risicoPercentage, exchangeRate, lotSize);
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getCalculationsForUser(String username) {
        List<String> calculations = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Username: " + username)) {
                    calculations.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return calculations;
    }
}