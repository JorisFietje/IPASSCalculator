package controller;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CalculatorPersistence {
    private static final String FILE_PATH = "calculations.txt";

    // Method to save a calculation to the file
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

    // Method to delete a calculation from the file
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

    // Method to retrieve all calculations from the file
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

    // Account statistics methods
    // Method to get the total number of calculations for a user
    public static int getTotalCalculations(String username) {
        return getCalculationsForUser(username).size();
    }

    // Method to get the most used currency pair for a user
    public static String getMostUsedCurrencyPair(String username) {
        List<String> calculations = getCalculationsForUser(username);
        Map<String, Integer> currencyPairCount = new HashMap<>();
        for (String calculation : calculations) {
            String currencyPair = calculation.split(", ")[7].split(": ")[1];
            currencyPairCount.put(currencyPair, currencyPairCount.getOrDefault(currencyPair, 0) + 1);
        }
        return currencyPairCount.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("No calculations made yet");
    }
}