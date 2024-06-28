package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class LoginServlet extends HttpServlet {
    private static final String FILE_PATH = "users.csv";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts[0].equals(username) && parts[1].equals(password)) {
                    // Create a session for the logged in user
                    HttpSession session = request.getSession();
                    session.setAttribute("username", username);

                    // Redirect to the rekenmachine.jsp page
                    response.sendRedirect("rekenmachine.jsp");
                    return;
                }
            }

            response.getWriter().println("Ongeldige gebruikersnaam of wachtwoord.");
        } catch (IOException e) {
            e.printStackTrace();
            response.getWriter().println("Er is een fout opgetreden bij het inloggen.");
        }
    }
}
