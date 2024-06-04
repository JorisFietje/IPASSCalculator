package controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

public class CalculatorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>Lot Size Calculator</title></head>");
        out.println("<body>");
        out.println("<h1>Lot Size Calculator</h1>");

        try {
            double saldo = Double.parseDouble(request.getParameter("saldo"));
            double risicoPercentage = Double.parseDouble(request.getParameter("risicoPercentage"));
            double stopLoss = Double.parseDouble(request.getParameter("stopLoss"));
            String valutapaar = request.getParameter("valutapaar");

            // Ophalen van de actuele wisselkoers van het valutapaar
            double exchangeRate = getExchangeRate(valutapaar);

            // Berekenen van de lot size
            double lotSize = calculateLotSize(saldo, risicoPercentage, stopLoss, exchangeRate);

            // Teruggeven van de berekende lot size als serverresponse
            out.println("<p>Actuele lot size voor valutapaar " + valutapaar + ": " + lotSize + "</p>");

            // Bijwerken van de serverresponse div op de rekenmachine.html pagina met de berekende lotgrootte
            out.println("<script>");
            out.println("document.getElementById('serverresponse').innerHTML = 'Actuele lot size voor valutapaar " + valutapaar + ": " + lotSize + "';");
            out.println("</script>");

        } catch (NumberFormatException e) {
            out.println("<p>Invalid input. Please enter valid numerical values.</p>");
        }

        out.println("</body>");
        out.println("</html>");
    }

    // Methode om de actuele wisselkoers op te halen van een valutapaar via de Alpha Vantage API
    private double getExchangeRate(String valutapaar) throws IOException {
        String apiKey = "AD7SMJO91RSC1YYD"; // Vervang dit door je eigen API-sleutel
        String apiUrl = "https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency="
                + valutapaar.substring(0, 3) + "&to_currency=" + valutapaar.substring(3) + "&apikey=" + apiKey;

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

    // Methode om de lot size te berekenen
    private double calculateLotSize(double saldo, double risicoPercentage, double stopLoss, double exchangeRate) {
        // Bepaal het bedrag dat u bereid bent te riskeren op basis van het risicopercentage
        double riskAmount = saldo * (risicoPercentage / 100);

        // Bepaal de afstand van de instapprijs tot de stop loss in de valutaprijs (in de basisvaluta)
        double stopLossDistance = Math.abs(exchangeRate - stopLoss);

        // Bereken de grootte van de positie (lot size)
        double lotSize = riskAmount / stopLossDistance;

        return lotSize;
    }
}
