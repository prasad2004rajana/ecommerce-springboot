import { useState } from "react";
import api from "../../api/axios";
import "./Auth.css";

export function RegisterPage() {
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleRegister = async () => {
    try {
        const response = await api.post("/auth/register", {
            firstName,
            lastName,
            email,
            password
        });

        alert("Registration successful!");

        console.log(response.data);

    } catch (error) {
        console.error(error);

        alert(
            error.response?.data?.message ||
            "Registration failed."
        );
    }
};

  return (
    <div className="auth-container">
      <div className="auth-card">
        <h2>Register</h2>

        <div className="form-group">
    <label>First Name</label>
    <input
        type="text"
        placeholder="Enter first name"
        value={firstName}
        onChange={(e) => setFirstName(e.target.value)}
    />
</div>

<div className="form-group">
    <label>Last Name</label>
    <input
        type="text"
        placeholder="Enter last name"
        value={lastName}
        onChange={(e) => setLastName(e.target.value)}
    />
</div>
        <div className="form-group">
          <label>Email</label>
          <input
            type="email"
            placeholder="Enter your email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </div>

        <div className="form-group">
          <label>Password</label>
          <input
            type="password"
            placeholder="Enter your password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>

        <button className="auth-button" onClick={handleRegister}>
           Register
        </button>
      </div>
    </div>
  );
}