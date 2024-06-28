<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Account</title>
    <link rel="stylesheet" href="styles.css?v=1.1">
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
        <h2>Account</h2>
        <p>Welcome, ${sessionScope.username}!</p>
        <form action="ChangeCredentialsServlet" method="post">
            <label for="currentPassword">Current Password:</label>
            <input type="password" id="currentPassword" name="currentPassword" required>
            <label for="newUsername">New Username:</label>
            <input type="text" id="newUsername" name="newUsername">
            <label for="newPassword">New Password:</label>
            <input type="password" id="newPassword" name="newPassword" required>
            <input type="submit" value="Change Credentials">
        </form>
    </div>
</div>
</body>
</html>