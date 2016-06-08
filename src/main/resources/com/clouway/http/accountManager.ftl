<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>AccountManager</title>
  </head>
  <body>
    <h1>Users in the system : ${loggedUsers}</h1>
    </br>
    <a href="logout">LOGOUT</a>
    <h1>Balance is : ${balance}</h1>
    </br>
    <form action="balance" name="balanceForm" method="post">
    <input type="text" name="amount" placeholder="amount" /> 
    <span class="incorrect">${errorMessage}</span>
    </br>
    <input type="submit" name="transactionType" value="withdraw" /> 
    <input type="submit" name="transactionType" value="deposit" /></form>
  </body>
</html>
