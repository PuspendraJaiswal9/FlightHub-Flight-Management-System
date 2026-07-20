import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";
import "./Dashboard.css";

export default function Dashboard() {
  const navigate = useNavigate();
  const [stats, setStats] = useState({
    totalFlights: 0,
    totalBookings: 0,
    totalRevenue: 0,
    userBookings: 0,
    userSpent: 0,
  });
  const [userRole, setUserRole] = useState("");
  const [username, setUsername] = useState("");

  const token = localStorage.getItem("token");

  useEffect(() => {
    const initUser = () => {
      if (token) {
        try {
          const decoded = jwtDecode(token);
          const roles = JSON.parse(localStorage.getItem("roles") || "[]");
          setUsername(decoded.sub || "");
          setUserRole(roles.includes("ROLE_ADMIN") ? "ADMIN" : "USER");
        } catch (e) {
          console.error("Token decode error:", e);
        }
      }
    };

    const loadStats = async () => {
      try {
        const decoded = jwtDecode(token);
        const roles = JSON.parse(localStorage.getItem("roles") || "[]");
        const isAdmin = roles.includes("ROLE_ADMIN");
        const currentUser = decoded.sub || "";

        const flightsRes = await fetch("http://localhost:8081/flight", {
          headers: { Authorization: `Bearer ${token}` },
        });
        const flights = flightsRes.ok ? await flightsRes.json() : [];

        const bookingsRes = await fetch("http://localhost:8082/bookings/all", {
          headers: { Authorization: `Bearer ${token}` },
        });
        const bookings = bookingsRes.ok ? await bookingsRes.json() : [];

        if (isAdmin) {
          const revenue = bookings.reduce((sum, b) => sum + (b.fareAmount || 0), 0);
          setStats({
            totalFlights: flights.length,
            totalBookings: bookings.length,
            totalRevenue: revenue,
            userBookings: 0,
            userSpent: 0,
          });
        } else {
          const userBookings = bookings.filter(b => 
            (b.passengerName || "").toLowerCase() === currentUser.toLowerCase()
          );
          const userSpent = userBookings.reduce((sum, b) => sum + (b.fareAmount || 0), 0);
          setStats({
            totalFlights: flights.length,
            totalBookings: 0,
            totalRevenue: 0,
            userBookings: userBookings.length,
            userSpent: userSpent,
          });
        }
      } catch (err) {
        console.error("Error fetching stats:", err);
      }
    };

    initUser();
    loadStats();
  }, [token]);

  return (
    <div className="dashboard-container">
      <div className="welcome-section">
        <h1 className="welcome-title">Welcome back, {username}! 👋</h1>
        <p className="welcome-subtitle">
          {userRole === "ADMIN" 
            ? "Manage your flight operations from here" 
            : "Book your next flight and manage your bookings"}
        </p>
      </div>

      <div className="stats-grid">
        {userRole === "ADMIN" && (
          <>
            <div className="stat-card flights">
              <div className="stat-icon">✈️</div>
              <div className="stat-content">
                <div className="stat-value">{stats.totalFlights}</div>
                <div className="stat-label">Total Flights</div>
              </div>
            </div>

            <div className="stat-card bookings">
              <div className="stat-icon">🎫</div>
              <div className="stat-content">
                <div className="stat-value">{stats.totalBookings}</div>
                <div className="stat-label">All Bookings</div>
              </div>
            </div>

            <div className="stat-card revenue">
              <div className="stat-icon">💰</div>
              <div className="stat-content">
                <div className="stat-value">₹{stats.totalRevenue.toLocaleString()}</div>
                <div className="stat-label">Total Revenue</div>
              </div>
            </div>
          </>
        )}

        {userRole === "USER" && (
          <>
            <div className="stat-card flights">
              <div className="stat-icon">✈️</div>
              <div className="stat-content">
                <div className="stat-value">{stats.totalFlights}</div>
                <div className="stat-label">Available Flights</div>
              </div>
            </div>

            <div className="stat-card bookings">
              <div className="stat-icon">🎫</div>
              <div className="stat-content">
                <div className="stat-value">{stats.userBookings}</div>
                <div className="stat-label">My Bookings</div>
              </div>
            </div>

            <div className="stat-card spent">
              <div className="stat-icon">💳</div>
              <div className="stat-content">
                <div className="stat-value">₹{stats.userSpent.toLocaleString()}</div>
                <div className="stat-label">Total Spent</div>
              </div>
            </div>
          </>
        )}
      </div>

      <div className="quick-actions">
        <h2 className="section-title">Quick Actions</h2>
        <div className="actions-grid">
          {userRole === "ADMIN" && (
            <>
              <div className="action-card" onClick={() => navigate("/flight")}>
                <div className="action-icon">✈️</div>
                <h3>Manage Flights</h3>
                <p>Add, update or delete flights</p>
              </div>
              <div className="action-card" onClick={() => navigate("/fare")}>
                <div className="action-icon">💰</div>
                <h3>Manage Fares</h3>
                <p>Update pricing and discounts</p>
              </div>
            </>
          )}
          <div className="action-card" onClick={() => navigate("/booking")}>
            <div className="action-icon">🎫</div>
            <h3>New Booking</h3>
            <p>Book a new flight ticket</p>
          </div>
          <div className="action-card" onClick={() => navigate("/my-bookings")}>
            <div className="action-icon">📋</div>
            <h3>My Bookings</h3>
            <p>View and manage bookings</p>
          </div>
        </div>
      </div>
    </div>
  );
}
