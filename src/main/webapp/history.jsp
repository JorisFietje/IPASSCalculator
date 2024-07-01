<%@ page import="java.util.List" %>
<%@ page import="controller.CalculatorPersistence" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Calculation History</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link rel="stylesheet" href="styles/styles.css?v=1.1">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
</head>
<body>
<div class="container">
    <div class="sidebar">
        <h2>Lot Size Calculator</h2>
        <ul>
            <li><a href="rekenmachine.jsp">Calculator</a></li>
            <li><a href="history.jsp">History</a></li>
            <li><a href="about.jsp">About</a></li>
        </ul>
        <div class="account">
            <% if (session.getAttribute("username") == null) { %>
            <a href="logreg.html">Login/Register</a>
            <% } else { %>
            <a href="account.jsp"><i class="fas fa-user"></i></a>
            <a href="logout.jsp" class="logout"><i class="fas fa-sign-out-alt"></i></a>
            <% } %>
        </div>
    </div>
    <div class="main-content">
        <h2>Calculation History of ${sessionScope.username}</h2>
        <%
            String username = (String) session.getAttribute("username");
            List<String> calculations = CalculatorPersistence.getCalculationsForUser(username);
        %>
        <% if (calculations != null && !calculations.isEmpty()) { %>
        <table id="historyTable">
            <tr>
                <th>Currency Pair</th>
                <th>Lot Size</th>
                <th>Balance</th>
                <th>Exchange Rate</th>
                <th>Risk Percentage</th>
                <th id="dateHeader" onclick="sortTable()">Date</th>
                <th>Actions</th>
            </tr>
            <% for (String calculation : calculations) {
                String[] parts = calculation.split(", ");
                if (parts.length >= 8) {
                    String[] idPart = parts[0].split(": ");
                    String[] datePart = parts[2].split(": ");
                    String[] balancePart = parts[3].split(": ");
                    String[] riskPart = parts[4].split(": ");
                    String[] exchangePart = parts[5].split(": ");
                    String[] lotPart = parts[6].split(": ");
                    String[] valutapaarPart = parts[7].split(": ");
            %>
            <tr>
                <td><%= valutapaarPart[1] %></td>
                <td><%= lotPart[1] %></td>
                <td><%= balancePart[1] %></td>
                <td><%= exchangePart[1] %></td>
                <td><%= riskPart[1] %></td>
                <td><%= datePart[1] %></td>
                <td><button class="delete-button" data-id="<%= idPart[1] %>"><i class="fas fa-trash"></i></button></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
        <% } else { %>
        <p>No calculations made yet/login to save calculations</p>
        <% } %>
    </div>
</div>
<script>
    $(document).ready(function() {
        $('.delete-button').click(function(event) {
            // Show a confirmation dialog
            var confirmDelete = confirm('Are you sure you want to delete this item?');

            // If the user clicked Cancel, prevent the default action
            if (!confirmDelete) {
                event.preventDefault();
                return;
            }

            var id = $(this).data('id');
            $.post('DeleteCalculationServlet', {id: id}, function(response) {
                // Regardless of the response, refresh the page
                location.reload();
            });
        });
    });
</script>
<script src="styles/script.js"></script>
</body>
</html>