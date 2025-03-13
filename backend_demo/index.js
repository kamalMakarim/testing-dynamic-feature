const express = require("express");

const app = express();
const port = 5000;

app.use(express.json());

app.post("/account/login", (req, res) => {
  const { email, password } = req.query;

  // Hardcoded credentials for testing
  const hardcodedUsername = "kamal.makarim@gmail.com";
  const hardcodedPassword = "Password1234";
  const hardcodedAdminUsername = "admin@gmail.com";
  const hardcodedAdminPassword = "Password1234";

  if (email === hardcodedUsername && password === hardcodedPassword) {
    res
      .status(200)
      .send({
        success: true,
        message: "Login successful",
        payload: {
          email: "kamal.makarim@gmail.com",
          password: "Password1234",
          ID: "id_1234",
          role: "USER",
        },
      });
  }
  else if (email === hardcodedAdminUsername && password === hardcodedAdminPassword) {
    res
      .status(200)
      .send({
        success: true,
        message: "Login successful",
        payload: {
          email: "admin@gmail.com",
          password: "Password1234",
          ID: "id_1234",
          role: "ADMIN",
        },
      });
  }
   else {
    res.status(401).send({ success: false, message: "Invalid credentials" });
  }
});

app.listen(port, () => {
  console.log(`Server is running on http://localhost:${port}`);
});


//where can i put