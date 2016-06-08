<!DOCTYPE html>
<html lang="en">
  <head>
	<meta charset="UTF-8" />
    <title>Login</title>
  </head>
  <body>
  <form action="login" name="loginForm" method="post">
  <label>Email</label>
  <span class="incorrect">${wrongEmail}</span>
  </br>
  <input type="text" name="email" placeholder="Email" />
  </br>
  <label>Password</label> 
  <span class="incorrect">${wrongPassword}</span>
  </br>
  <input type="password" name="password" placeholder="Password" />
  </br>
  <input type="submit" value="Submit" />
  </br>
  <span class="incorrect">${wrongEmailOrPassword}</span></form></body>
</html>
