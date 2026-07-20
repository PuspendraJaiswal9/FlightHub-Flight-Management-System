import React, { useState, useEffect } from "react";
import { jwtDecode } from "jwt-decode";
import "./MyBookings.css";

export default function MyBookings() {
  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(false);
  const [userRole, setUserRole] = useState("");
  const [username, setUsername] = useState("");

  const token = localStorage.getItem("token");

  useEffect(() => {
    const initializeUser = () => {
      if (token) {
        try {
          const decoded = jwtDecode(token);
          const roles = JSON.parse(localStorage.getItem("roles") || "[]");
          const user = decoded.sub || "";
          setUsername(user);
          setUserRole(roles.includes("ROLE_ADMIN") ? "ADMIN" : "USER");
          console.log("Logged in user:", user);
          console.log("User role:", roles.includes("ROLE_ADMIN") ? "ADMIN" : "USER");
        } catch (e) {
          console.error("Token decode error:", e);
        }
      }
    };

    const loadBookings = async () => {
      setLoading(true);
      try {
        const res = await fetch("http://localhost:8082/bookings/all", {
          headers: { Authorization: `Bearer ${token}` },
        });

        if (res.ok) {
          const data = await res.json();
          console.log("Fetched bookings:", data);
          setBookings(Array.isArray(data) ? data : []);
        } else {
          console.error("Failed to fetch bookings, status:", res.status);
          setBookings([]);
        }
      } catch (err) {
        console.error("Error fetching bookings:", err);
        setBookings([]);
      } finally {
        setLoading(false);
      }
    };

    initializeUser();
    loadBookings();
  }, [token]);

  const fetchBookings = async () => {
    setLoading(true);
    try {
      const res = await fetch("http://localhost:8082/bookings/all", {
        headers: { Authorization: `Bearer ${token}` },
      });

      if (res.ok) {
        const data = await res.json();
        console.log("Fetched bookings:", data);
        setBookings(Array.isArray(data) ? data : []);
      } else {
        console.error("Failed to fetch bookings, status:", res.status);
        setBookings([]);
      }
    } catch (err) {
      console.error("Error fetching bookings:", err);
      setBookings([]);
    } finally {
      setLoading(false);
    }
  };

  const deleteBooking = async (bookingRef) => {
    if (!window.confirm(`Are you sure you want to delete booking ${bookingRef}?`)) {
      return;
    }

    try {
      const res = await fetch(`http://localhost:8082/bookings/${bookingRef}`, {
        method: "DELETE",
        headers: { Authorization: `Bearer ${token}` },
      });

      if (res.ok) {
        alert("Booking deleted successfully!");
        fetchBookings();
      } else {
        alert("Failed to delete booking");
      }
    } catch (err) {
      console.error("Error deleting booking:", err);
      alert("Error deleting booking");
    }
  };

  // Filter bookings based on user role
  const filteredBookings = userRole === "ADMIN" 
    ? bookings 
    : bookings.filter(b => {
        // User can only see their own bookings (match by passenger name)
        const bookingName = (b.passengerName || "").toLowerCase();
        const loggedInUser = username.toLowerCase();
        return bookingName === loggedInUser;
      });

  console.log("=== My Bookings Debug ===");
  console.log("User role:", userRole);
  console.log("Username:", username);
  console.log("Total bookings from backend:", bookings.length);
  console.log("All bookings:", bookings);
  console.log("Filtered bookings:", filteredBookings.length);
  console.log("Filtered data:", filteredBookings);

  if (loading) {
    return (
      <div className="my-bookings-container">
        <div className="loading">Loading bookings...</div>
      </div>
    );
  }

  return (
    <div className="my-bookings-container">
      <div className="bookings-header">
        <h2 className="bookings-title">
          {userRole === "ADMIN" ? "All Bookings" : "My Bookings"}
        </h2>
        <button className="refresh-btn" onClick={fetchBookings}>
          🔄 Refresh
        </button>
      </div>

      {filteredBookings.length === 0 ? (
        <div className="no-bookings">
          <div className="no-bookings-icon">📭</div>
          <h3>No Bookings Found</h3>
          <p>You don't have any bookings yet</p>
        </div>
      ) : (
        <div className="bookings-grid">
          {filteredBookings.map((booking) => (
            <div key={booking.bookingId} className="booking-card">
              <div className="booking-card-header">
                <div className="booking-ref">
                  <span className="ref-label">Booking Ref</span>
                  <span className="ref-value">{booking.bookingReference}</span>
                </div>
                <div className={`booking-status ${(booking.status || 'CONFIRMED').toLowerCase()}`}>
                  {booking.status || 'CONFIRMED'}
                </div>
              </div>

              <div className="booking-card-body">
                <div className="booking-info-row">
                  <div className="info-col">
                    <span className="info-icon">✈️</span>
                    <div>
                      <div className="info-label">Flight</div>
                      <div className="info-text">{booking.flightNumber}</div>
                    </div>
                  </div>
                  <div className="info-col">
                    <span className="info-icon">👤</span>
                    <div>
                      <div className="info-label">Passenger</div>
                      <div className="info-text">{booking.passengerName}</div>
                    </div>
                  </div>
                </div>

                <div className="booking-info-row">
                  <div className="info-col">
                    <span className="info-icon">📧</span>
                    <div>
                      <div className="info-label">Email</div>
                      <div className="info-text">{booking.email}</div>
                    </div>
                  </div>
                  <div className="info-col">
                    <span className="info-icon">📱</span>
                    <div>
                      <div className="info-label">Phone</div>
                      <div className="info-text">{booking.phone}</div>
                    </div>
                  </div>
                </div>

                <div className="booking-info-row">
                  <div className="info-col">
                    <span className="info-icon">📅</span>
                    <div>
                      <div className="info-label">Booking Date</div>
                      <div className="info-text">
                        {new Date(booking.bookingDate).toLocaleDateString()}
                      </div>
                    </div>
                  </div>
                  <div className="info-col">
                    <span className="info-icon">💰</span>
                    <div>
                      <div className="info-label">Fare</div>
                      <div className="info-text fare-amount">₹{booking.fareAmount}</div>
                    </div>
                  </div>
                </div>
              </div>

              <div className="booking-card-footer">
                {booking.status === "PENDING" && booking.sessionUrl && (
                  <button
                    className="payment-btn"
                    onClick={() => window.open(booking.sessionUrl, "_blank")}
                  >
                    💳 Complete Payment
                  </button>
                )}
                <button
                  className="delete-btn"
                  onClick={() => deleteBooking(booking.bookingReference)}
                >
                  🗑️ {userRole === "ADMIN" ? "Delete Booking" : "Cancel My Booking"}
                </button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
