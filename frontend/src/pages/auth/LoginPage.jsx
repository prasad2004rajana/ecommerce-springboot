import { useState } from "react";
import api from "../../api/axios";
import { Link } from 'react-router';
import { useNavigate } from "react-router";
import "./Auth.css";

export function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
        const response = await api.post("/auth/login",  {
            email,
            password
        });

        localStorage.setItem("token", response.data.token);

        alert("Login successful!");

        navigate("/");

    } catch (error) {
        console.error(error);

        alert(error.response?.data?.message || "Invalid email or password");
    }
};

  return (
    <div className="auth-container">
      <div className="auth-card">
        <h2>Login</h2>

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

        <button className="auth-button" onClick={handleLogin}>
           Login
        </button>
        <p>
    New user? <Link to="/register">Sign Up</Link>
</p>
      </div>
    </div>
  );
}