package controller;

import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class CalculatorServletTest {


    @Test
    public void testCalculateLotSize() {
        // Sample input values
        double saldo = 1000.0;
        double risicoPercentage = 2.0;
        double exchangeRate = 1.23;
        double stopLoss = 10.0;

        // Expected result
        double expectedLotSize = saldo * (risicoPercentage / 100) / exchangeRate;

        // Test calculateLotSize method
        CalculatorServlet calculatorServlet = new CalculatorServlet();
        double lotSize = calculatorServlet.calculateLotSize(saldo, risicoPercentage, exchangeRate, stopLoss);

        // Assert
        assertEquals(expectedLotSize, lotSize, 0.01);
    }

}