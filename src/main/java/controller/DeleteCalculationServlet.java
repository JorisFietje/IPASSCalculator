package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteCalculationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String id = request.getParameter("id");
            // Call the method to delete the calculation
            CalculatorPersistence.deleteCalculation(id);
            // Send a response back to the client
            response.getWriter().write("success");
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            // Send a response back to the client
            response.getWriter().write("error");
        }
    }
}