package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class RegisterServlet extends HttpServlet {
   private static final String FILE_PATH = "users.csv"; // Zorg ervoor dat dit pad correct is

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

//        // Debugging: Log de inkomende gegevens
//        System.out.println("Registering user: " + username);

        BufferedWriter writer = null;
        try {
            // Controleer of het bestand bestaat, zo niet, maak het aan
            if (!Files.exists(Paths.get(FILE_PATH))) {
                Files.createFile(Paths.get(FILE_PATH));
            }

            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts[0].equals(username)) {
                    response.getWriter().println("Gebruikersnaam bestaat al!");
                    return;
                }
            }

            writer = new BufferedWriter(new FileWriter(FILE_PATH, true));
            writer.write(username + "," + password);
            writer.newLine();

            // After successful registration, create a session for the new user
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            // Redirect to the rekenmachine.jsp page
            response.sendRedirect("rekenmachine.jsp");
        } catch (IOException e) {
            e.printStackTrace();
            response.getWriter().println("Er is een fout opgetreden bij de registratie.");

        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}