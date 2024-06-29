<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Lot Size Calculator</title>
    <link rel="stylesheet" href="styles.css?v=1.1">
    <script src="script.js" defer></script>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
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
        <h2>Lot Size Calculator</h2>
        <p>
            <% if (session.getAttribute("username") == null) { %>
            Log in to save calculations
            <% } else { %>
            Welcome, ${sessionScope.username}!
            <% } %>
        </p>
        <form id="rekenmachine" action="rekenmachine" method="get">
            <label for="accountCurrency">Account Currency</label>
            <select id="accountCurrency" name="accountCurrency">
                <option value="EUR">EUR</option>
            </select>

            <label for="saldo">Account Balance</label>
            <input type="number" id="saldo" name="saldo" placeholder="Balance" step="0.01" required>

            <label for="risicoPercentage">Risk Percentage %</label>
            <input type="number" id="risicoPercentage" name="risicoPercentage" placeholder="Risk %" step="0.01" required>

            <label for="valutapaar">Currency Pair</label>
            <select id="valutapaar" name="valutapaar">
                <option value="EURUSD">EUR/USD</option>
                <option value="GBPJPY">GBP/JPY</option>
                <option value="GBPUSD">GBP/USD</option>
            </select>


            <input type="submit" value="Calculate">
        </form>
        <div id="result">
            <h3>Result:</h3>
            <div id="serverresponse"></div>
        </div>
    </div>
</div>
<script src="script.js"></script>
</body>
</html>
