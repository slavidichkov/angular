<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>Registration</title>
  </head>
  <body>
    <form action="registration" name="registrationForm" method="post">
    <label>Name</label> 
    <span class="incorrect">${wrongName}</span>
    <br />
    <input type="text" name="name" placeholder="Name" />
    <br />
    <label>Nick Name</label> 
    <span class="incorrect">${wrongNickName}</span>
    <br />
    <input type="text" name="nickName" placeholder="Nick Name" />
    <br />
    <label>Age</label> 
    <span class="incorrect">${wrongAge}</span>
    <br />
    <input type="text" name="age" placeholder="Age" />
    <br />
    <label>City</label> 
    <span class="incorrect">${wrongCity}</span>
    <br />
    <input type="text" name="city" placeholder="City" />
    <br />
    <label>Email</label> 
    <span class="incorrect">${wrongEmail}</span>
    <br />
    <input type="text" name="email" placeholder="Email" />
    <br />
    <label>Password</label> 
    <span class="incorrect">${wrongPassword}</span>
    <br />
    <input type="password" name="password" placeholder="Password" />
    <br />
    <label>Confirm Password</label> 
    <span class="incorrect">${wrongConfirmPassword}</span>
    <br />
    <input type="password" name="confirmPassword" placeholder="Confirm Password" />
    <br />
    <input type="submit" value="Submit" /></form>
  </body>
</html>
