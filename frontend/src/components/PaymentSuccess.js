import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./PaymentSuccess.css";

export default function PaymentSuccess() {
  const navigate = useNavigate();
  const [booking, setBooking] = useState(null);
  const token = localStorage.getItem("token");

  useEffect(() => {
    const confirmAndFetchBooking = async () => {
      const ref = localStorage.getItem("latestBookingRef");
      if (!ref) return;

      try {
        // Confirm booking status
        await fetch(`http://localhost:8082/bookings/confirm/${ref}`, {
          method: "PUT",
          headers: { Authorization: `Bearer ${token}` },
        });

        // Fetch updated booking details
        const res = await fetch(`http://localhost:8082/bookings/${ref}`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        if (res.ok) {
          const data = await res.json();
          setBooking(data);
        }
      } catch (err) {
        console.error("Error confirming/fetching booking:", err);
      }
    };
    confirmAndFetchBooking();
  }, [token]);

  return (
    <div className="payment-success-container">
      <div className="success-icon">✓</div>
      <h1 className="payment-success-title">Payment Successful!</h1>
      <p className="payment-success-subtitle">Your booking has been confirmed</p>

      {booking && (
        <div className="payment-success-details">
          <h3 className="payment-success-details-title">🎫 Booking Confirmation</h3>
          
          <div className="booking-info-grid">
            <div className="info-item">
              <div className="info-label">Booking Reference</div>
              <div className="info-value">{booking.bookingReference}</div>
            </div>
            
            <div className="info-item">
              <div className="info-label">Flight Number</div>
              <div className="info-value">{booking.flightNumber}</div>
            </div>
            
            <div className="info-item">
              <div className="info-label">Passenger Name</div>
              <div className="info-value">{booking.passengerName}</div>
            </div>
            
            <div className="info-item">
              <div className="info-label">Email</div>
              <div className="info-value">{booking.email}</div>
            </div>
            
            <div className="info-item">
              <div className="info-label">Phone</div>
              <div className="info-value">{booking.phone}</div>
            </div>
            
            <div className="info-item">
              <div className="info-label">Status</div>
              <div className="info-value status">{booking.status}</div>
            </div>
            
            <div className="info-item">
              <div className="info-label">Booking Date</div>
              <div className="info-value">{new Date(booking.bookingDate).toLocaleDateString()}</div>
            </div>
            
            <div className="info-item">
              <div className="info-label">Total Fare</div>
              <div className="info-value fare">₹{booking.fareAmount}</div>
            </div>
          </div>
        </div>
      )}

      <button className="payment-success-button" onClick={() => navigate("/")}>
        Go to Dashboard
      </button>
    </div>
  );
}
