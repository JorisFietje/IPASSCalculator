package controller;

import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class CalculatorServletTest {


    @Test
    public void testCalculateLotSize() {
        // Sample input values
        String valutapaar = "GBPEUR";
        double inlegbedrag = 5000;
        double risicoPercentage = 100;
        double exchangeRate = 1.18;
        double stopLoss = 10.0;

        // Expected result
        double tradedmoney = inlegbedrag * (risicoPercentage / 100);
        System.out.println("Traded Money: " + tradedmoney);
        double exchange = tradedmoney / exchangeRate;
        System.out.println("GBP price: " + exchange);
        double lotsize = exchange / 1000;
        System.out.println("lotsize: " + lotsize);

        // Test calculateLotSize method
        CalculatorServlet calculatorServlet = new CalculatorServlet();
       // double lotSize = calculatorServlet.calculateLotSize(inlegbedrag, risicoPercentage, exchangeRate);

//        System.out.println("Lot Size: " + lotSize);
        // Assert
       // assertEquals(exchange, lotSize, 0.01);
    }

}