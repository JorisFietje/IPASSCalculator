<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>About</title>
    <link rel="stylesheet" href="styles/styles.css?v=1.1">
    <script src="styles/script.js" defer></script>
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
        <h2>About</h2>
        <section class="about-text">
            <article>
                <h3>What is this website?</h3>
                <p>The Lot Size Calculator is a web application designed to assist users in the Forex trading market. It calculates the lot size based on the user's account balance, risk percentage, and the currency pair they are trading with.</p>


                <h3>How does it work?</h3>
                <p>The process begins when a user inputs their account balance, the percentage of the balance they are willing to risk, and the currency pair they are trading with. The application then calculates the lot size, which is the number of currency units a trader will buy or sell in a Forex trade.</p>
                <p>
                    For example, if a user has an account balance of 1000 EUR, wants to risk 2% of the account balance, and is trading with the EUR/USD currency pair, the application will calculate the lot size for the user. This helps the user to manage their risk effectively by not risking more than a certain percentage of their account balance on a single trade.
                </p>
                <p>
                    The application also allows users to save their calculations. This feature is useful for users who want to keep track of their trading decisions and analyze their performance over time. The saved calculations can be viewed in the "History" page.
                </p>
                <p>
                    Users can also register an account and login to access more features. For example, logged-in users can view their account statistics and logout from the application.
                </p>
            </article>
        </section>
    </div>
</div>
<script src="styles/script.js"></script>
</body>
</html>
