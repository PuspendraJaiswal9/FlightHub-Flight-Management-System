import { useState } from "react";
import { jwtDecode } from "jwt-decode";
import "./AuthPage.css";

export default function AuthPage({ onLoginSuccess }) {
  const [isLogin, setIsLogin] = useState(true);
  const [formData, setFormData] = useState({
    username: "",
    email: "",
    password: "",
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
    setError("");
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");
    setSuccess("");

    try {
      if (isLogin) {
        const res = await fetch("http://localhost:9090/user/login", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            username: formData.username,
            password: formData.password,
          }),
        });

        if (res.status === 400 || res.status === 401) {
          throw new Error("Invalid username or password. Please try again.");
        }
        if (res.status === 500) {
          throw new Error("Server error. Please make sure backend is running.");
        }
        if (!res.ok) {
          const text = await res.text();
          throw new Error(text || "Login failed. Please try again.");
        }

        const data = await res.json();
        if (!data.token) throw new Error("No token received. Please try again.");

        localStorage.setItem("token", data.token);
        const decoded = jwtDecode(data.token);
        const roles = decoded.roles || [];
        localStorage.setItem("roles", JSON.stringify(roles));

        setSuccess("Login successful! Redirecting...");
        setTimeout(() => {
          if (onLoginSuccess) onLoginSuccess();
        }, 1000);
      } else {
        const res = await fetch("http://localhost:9090/user/register", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            username: formData.username,
            email: formData.email,
            password: formData.password,
          }),
        });

        if (res.status === 409 || res.status === 400) {
          throw new Error("Username or email already exists. Please use different credentials.");
        }
        if (res.status === 500) {
          throw new Error("Server error. Please make sure backend is running.");
        }
        if (!res.ok) {
          const text = await res.text();
          throw new Error(text || "Registration failed. Please try again.");
        }

        await res.json();
        setSuccess("Registration successful! Please login.");
        setTimeout(() => {
          setIsLogin(true);
          setFormData({ username: "", email: "", password: "" });
          setSuccess("");
        }, 2000);
      }
    } catch (err) {
      if (err.message === "Failed to fetch") {
        setError("Cannot connect to server. Please make sure backend is running on port 9090.");
      } else {
        setError(err.message);
      }
    } finally {
      setLoading(false);
    }
  };

  const toggleMode = () => {
    setIsLogin(!isLogin);
    setFormData({ username: "", email: "", password: "" });
    setError("");
    setSuccess("");
  };

  return (
    <div className="auth-container">
      <div className="auth-left">
        <div className="auth-branding">
          <h1>✈️ FlightHub</h1>
          <p>Your trusted flight management system</p>
          <div className="auth-features">
            <div className="feature-item">
              <span className="feature-icon">✓</span>
              <span>Real-time flight booking</span>
            </div>
            <div className="feature-item">
              <span className="feature-icon">✓</span>
              <span>Secure payment processing</span>
            </div>
            <div className="feature-item">
              <span className="feature-icon">✓</span>
              <span>Instant confirmation</span>
            </div>
          </div>
        </div>
      </div>

      <div className="auth-right">
        <div className="auth-box">
          <div className="auth-header">
            <h2>{isLogin ? "Welcome Back" : "Create Account"}</h2>
            <p>{isLogin ? "Sign in to continue to FlightHub" : "Sign up to get started"}</p>
          </div>

          {error && <div className="alert alert-error">{error}</div>}
          {success && <div className="alert alert-success">{success}</div>}

          <form onSubmit={handleSubmit}>
            <div className="input-group">
              <label>Username</label>
              <input
                type="text"
                name="username"
                placeholder="Enter your username"
                value={formData.username}
                onChange={handleChange}
                required
              />
            </div>

            {!isLogin && (
              <div className="input-group">
                <label>Email Address</label>
                <input
                  type="email"
                  name="email"
                  placeholder="Enter your email"
                  value={formData.email}
                  onChange={handleChange}
                  required
                />
              </div>
            )}

            <div className="input-group">
              <label>Password</label>
              <input
                type="password"
                name="password"
                placeholder="Enter your password"
                value={formData.password}
                onChange={handleChange}
                required
                minLength="6"
              />
            </div>

            <button type="submit" className="submit-btn" disabled={loading}>
              {loading ? (
                <span className="loading-spinner"></span>
              ) : (
                isLogin ? "Sign In" : "Create Account"
              )}
            </button>
          </form>

          <div className="auth-footer">
            <p>
              {isLogin ? "Don't have an account?" : "Already have an account?"}
              <button className="toggle-btn" onClick={toggleMode}>
                {isLogin ? "Sign Up" : "Sign In"}
              </button>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}
