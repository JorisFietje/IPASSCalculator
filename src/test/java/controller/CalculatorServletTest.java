package controller;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculatorServletTest {
    private CalculatorServlet calculatorServlet;

    @Before
    public void setUp() {
        calculatorServlet = new CalculatorServlet();
    }

    @Test
    public void testCalculateLotSize() {
        double saldo = 5000;
        double risicoPercentage = 100;
        double exchangeRate = 1.18;
        double expectedLotSize = (saldo * (risicoPercentage / 100)) / (exchangeRate * 1000);
        double actualLotSize = calculatorServlet.calculateLotSize(saldo, risicoPercentage, exchangeRate);
        assertEquals(expectedLotSize, actualLotSize, 0.01);
    }
}