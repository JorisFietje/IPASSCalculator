package controller;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class ChangePasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String currentPassword = request.getParameter("currentPassword");
            String newPassword = request.getParameter("newPassword");

            // Retrieve the user from the session
            String username = (String) request.getSession().getAttribute("username");

            // Check if the current password is correct
            List<String> users = new ArrayList<>();
            String line;
            boolean passwordChanged = false;

            try (BufferedReader reader = new BufferedReader(new FileReader("users.csv"))) {
                while ((line = reader.readLine()) != null) {
                    String[] user = line.split(",");
                    if (user[0].equals(username) && user[1].equals(currentPassword)) {
                        users.add(username + "," + newPassword);
                        passwordChanged = true;
                    } else {
                        users.add(line);
                    }
                }
            }

            if (!passwordChanged) {
                // Redirect back to the account page with an error parameter
                response.sendRedirect("account.jsp?error=true");
                return;
            }

            // Update the password in the users.csv file
            try (PrintWriter writer = new PrintWriter(new FileWriter("users.csv"))) {
                for (String user : users) {
                    writer.println(user);
                }
            }

            // Redirect back to the account page with a success parameter
            response.sendRedirect("account.jsp?success=true");
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            // Send a response back to the client
            response.getWriter().write("error");
        }
    }
}