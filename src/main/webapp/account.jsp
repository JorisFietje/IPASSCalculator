<%@ page import="controller.CalculatorPersistence" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Account</title>
    <link rel="stylesheet" href="styles/styles.css?v=1.1">
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
</head>
<body>
<%
    // If no user is logged in, redirect to the login page
    if (session.getAttribute("username") == null) {
        response.sendRedirect("logreg.html");
        return;
    }
%>
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
        <h2>Account of ${sessionScope.username}</h2>
        <h3>User Statistics</h3>
        <div class="user-statistics">
            <div class="statistic">
                <span class="statistic-title">Total Calculations:</span>
                <span class="statistic-value"><%= CalculatorPersistence.getTotalCalculations((String) session.getAttribute("username")) %></span>
            </div>
            <div class="statistic">
                <span class="statistic-title">Most Used Currency Pair:</span>
                <span class="statistic-value"><%= CalculatorPersistence.getMostUsedCurrencyPair((String) session.getAttribute("username")) %></span>
            </div>
        </div>
        <h3>Change Password</h3>
        <form action="ChangePasswordServlet" method="post">
            <label for="currentPassword">Current Password:</label>
            <input type="password" id="currentPassword" name="currentPassword" required pattern=".{8,}" title="Password should be at least 8 characters long.">
            <label for="newPassword">New Password:</label>
            <input type="password" id="newPassword" name="newPassword" required pattern=".{8,}" title="Password should be at least 8 characters long.">
            <input type="submit" value="Change Password">
        </form>
    </div>
</div>
<%
    String success = request.getParameter("success");
    String error = request.getParameter("error");
    if ("true".equals(success)) {
%>
<script>
    alert("Successfully changed password");
</script>
<%
} else if ("true".equals(error)) {
%>
<script>
    alert("Incorrect current password");
</script>
<%
    }
%>
</body>
</html>