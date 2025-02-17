package controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;


public class CalculatorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<body>");

        try {
            // Getters are used here to retrieve the parameters from the request
            String saldoStr = request.getParameter("saldo");
            String risicoPercentageStr = request.getParameter("risicoPercentage");
            String valutapaar = request.getParameter("valutapaar");
            String accountCurrency = request.getParameter("accountCurrency");

            if (saldoStr == null || risicoPercentageStr == null || valutapaar == null) {
                throw new IllegalArgumentException("Missing parameter(s)");
            }

            double saldo = Double.parseDouble(saldoStr);
            double risicoPercentage = Double.parseDouble(risicoPercentageStr);

            // Ophalen van de actuele wisselkoers van het valutapaar
            double exchangeRate = getExchangeRate(valutapaar);

            // Berekenen van het risicobedrag
            double riskAmount = saldo * (risicoPercentage / 100);

            // Berekenen van de lot size
            double lotSize = calculateLotSize(saldo, risicoPercentage, exchangeRate);


            // Get the current session
            HttpSession session = request.getSession();

            // A getter is used here to retrieve the username from the session
            String username = (String) session.getAttribute("username");

            Locale.setDefault(Locale.US);
            // Teruggeven van de berekende lot size als serverresponse
            out.println("<p>Account Currency: " + accountCurrency + "</p>");
            out.println("<p>Currency Pair: " + valutapaar + "</p>");
            out.println("<p>Exchange Rate: " + exchangeRate + " </p>");
            out.println("<p>Risk Amount: " + riskAmount + " </p>");
            out.println("<p>Lot Size: " + String.format(Locale.US, "%.4f", lotSize) + "</p>");
            String formattedLotSize = String.format(Locale.US, "%.4f", lotSize);

            // Bijwerken van de serverresponse div op de rekenmachine.jsp pagina met de berekende lotgrootte
            out.println("<script>");
            out.println("document.getElementById('serverresponse').innerHTML = 'Valutapaar: " + valutapaar + "<br/>Actuele wisselkoers: " + exchangeRate + "<br/>Risicobedrag: " + riskAmount + "<br/>Lot Size: " + formattedLotSize + "';");
            out.println("</script>");


            CalculatorPersistence.saveCalculation(username, saldo, risicoPercentage, exchangeRate, lotSize, valutapaar);


        } catch (NumberFormatException e) {
            out.println("<p>Invalid input. Please enter valid numerical values.</p>");
        } catch (IllegalArgumentException e) {
            out.println("<p>" + e.getMessage() + "</p>");
        }
        out.println("</body>");
        out.println("</html>");


    }


    // Methode om de actuele wisselkoers op te halen van een valutapaar via de Alpha Vantage API
    private double getExchangeRate(String valutapaar) throws IOException {
        String apiKey = "HH31BS231AXR7W5P"; // Vervang dit door je eigen API-sleutel
        String apiUrl = "https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency="
                + valutapaar.substring(0, 3) + "&to_currency=EUR&apikey=" + apiKey;

        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        // Controleren op een succesvolle verbinding
        if (conn.getResponseCode() != 200) {
            throw new IOException("Error: Failed to connect to API. Response code: " + conn.getResponseCode());
        }

        // Gegevens lezen vanuit de API-respons
        InputStreamReader reader = new InputStreamReader(conn.getInputStream());
        Scanner scanner = new Scanner(reader);
        StringBuilder apiResponse = new StringBuilder();
        while (scanner.hasNext()) {
            apiResponse.append(scanner.nextLine());
        }
        scanner.close();

        // De wisselkoers parsen uit de JSON-respons
        String jsonResponse = apiResponse.toString();
        JSONObject jsonObject = new JSONObject(jsonResponse);
        double exchangeRate = jsonObject.getJSONObject("Realtime Currency Exchange Rate").getDouble("5. Exchange Rate");

        return exchangeRate;
    }

    // Methode om de lotgrootte te berekenen op basis van het saldo, risicopercentage en wisselkoers
    double calculateLotSize(double saldo, double risicoPercentage, double exchangeRate) {
        // Calculate the risk amount
        double riskAmount = saldo * (risicoPercentage / 100);

        // Determine the pip value based on the currency pair
        double currencyValue = riskAmount / exchangeRate;

        // Calculate the lot size
        double lotSize = currencyValue / 1000;

        return lotSize;
    }
}
