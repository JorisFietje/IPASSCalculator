<%@ page import="java.util.List" %>
<%@ page import="controller.CalculatorPersistence" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Calculation History</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link rel="stylesheet" href="styles.css?v=1.1">
</head>
<body>
<div class="container">
    <div class="sidebar">
        <h2>Lot Size Calculator</h2>
        <ul>
            <li><a href="rekenmachine.jsp">Calculator</a></li>
            <li><a href="history.jsp">History</a></li>
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
        <table>
            <tr>
                <th>Date</th>
                <th>Balance</th>
                <th>Risk Percentage</th>
                <th>Exchange Rate</th>
                <th>Lot Size</th>
            </tr>
            <% for (String calculation : calculations) {
                String[] parts = calculation.split(", ");
                String[] datePart = parts[1].split(": ");
                String[] balancePart = parts[2].split(": ");
                String[] riskPart = parts[3].split(": ");
                String[] exchangePart = parts[4].split(": ");
                String[] lotPart = parts[5].split(": ");
            %>
            <tr>
                <td><%= datePart[1] %></td>
                <td><%= balancePart[1] %></td>
                <td><%= riskPart[1] %></td>
                <td><%= exchangePart[1] %></td>
                <td><%= lotPart[1] %></td>
            </tr>
            <% } %>
        </table>
        <% } else { %>
        <p>No calculations made yet.</p>
        <% } %>
    </div>
</div>
</body>
</html>