package controller;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CalculatorPersistence {
    private static final String FILE_PATH = "calculations.txt";

    public static void saveCalculation(String username, double saldo, double risicoPercentage, double exchangeRate, double lotSize, String valutapaar) {
        // Check if the username is null or empty
        if (username == null || username.isEmpty()) {
            // If it is, return without saving the calculation
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Generate a unique ID for the calculation
            String id = UUID.randomUUID().toString();

            // Format the exchange rate and lot size separately
            String formattedExchangeRate = String.format("%.4f", exchangeRate).replace(",", ".");

            // Format the data and write it to the file
            String data = String.format("ID: %s, Username: %s, Date: %s, Balance: %.2f, Risk Percentage: %s, Exchange Rate: %s, Lot Size: %.4f, Currency Pair: %s%n",
                    id, username, now.format(formatter), saldo, risicoPercentage, formattedExchangeRate, lotSize, valutapaar);
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCalculation(String id) {
        List<String> calculations = new ArrayList<>();
        String line;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("ID: " + id)) {
                    calculations.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String calculation : calculations) {
                writer.write(calculation);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getCalculationsForUser(String username) {
    List<String> calculations = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains("Username: " + username)) {
                calculations.add(line);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return calculations;
}
}