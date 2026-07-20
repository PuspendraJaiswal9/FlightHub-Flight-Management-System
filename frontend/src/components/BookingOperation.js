import React, { useState } from "react";
import { loadStripe } from "@stripe/stripe-js";
import "./BookingOperation.css";

const stripePromise = loadStripe("pk_test_51RY008Qp1eyEiksj3NkLSUAMyrHhCjARJvUzUIILUbJrkCW3gzZdOZaHNdU8i3EHjlkjPVAqSYYYqXyHkhLTF10600IVx36azv");

export default function BookingOperation() {
  const [operation, setOperation] = useState("");
  const [bookingRef, setBookingRef] = useState("");
  const [booking, setBooking] = useState(null);
  const [loading, setLoading] = useState(false);

  const token = localStorage.getItem("token");
  
  // Get logged-in user info
  const getLoggedInUser = () => {
    if (token) {
      try {
        const decoded = JSON.parse(atob(token.split('.')[1]));
        return decoded.sub || "";
      } catch (e) {
        return "";
      }
    }
    return "";
  };

  const loggedInUsername = getLoggedInUser();

  const [bookingData, setBookingData] = useState({
    flightNumber: "",
    passengerName: loggedInUsername,
    email: "",
    phone: "",
    seatCount: 1,
    bookingDate: new Date().toISOString(),
  });

  const handleChange = (e) =>
    setBookingData({ ...bookingData, [e.target.name]: e.target.value });

  const addBooking = async () => {
    // Validation
    if (!bookingData.flightNumber || !bookingData.passengerName || !bookingData.email || !bookingData.phone) {
      alert("Please fill all required fields!");
      return;
    }

    try {
      setLoading(true);
      
      const payload = {
        flightNumber: bookingData.flightNumber,
        passengerName: bookingData.passengerName,
        email: bookingData.email,
        phone: bookingData.phone,
        seatCount: parseInt(bookingData.seatCount) || 1,
        bookingDate: new Date().toISOString()
      };

      console.log("Booking payload:", payload);

      const res = await fetch("http://localhost:8082/bookings/book", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(payload),
      });

      if (!res.ok) {
        const errorText = await res.text();
        console.error("Booking error:", errorText);
        throw new Error(errorText || "Booking failed");
      }
      
      const data = await res.json();
      console.log("Booking response:", data);

      // Save booking reference for success page
      if (data.bookingReference) {
        localStorage.setItem("latestBookingRef", data.bookingReference);
      }

      // Stripe payment redirect
      if (data.sessionId) {
        const stripe = await stripePromise;
        const { error } = await stripe.redirectToCheckout({
          sessionId: data.sessionId,
        });
        if (error) {
          console.error("Stripe error:", error);
          alert("Stripe Error: " + error.message);
        }
      } else {
        alert("Booking created but no payment session found!");
      }
    } catch (err) {
      console.error("Booking error:", err);
      alert("Booking failed: " + err.message);
    } finally {
      setLoading(false);
    }
  };

  const getBooking = async () => {
    if (!bookingRef.trim()) {
      alert("Please enter booking reference!");
      return;
    }

    try {
      setLoading(true);
      const res = await fetch(`http://localhost:8082/bookings/${bookingRef}`, {
        headers: { Authorization: `Bearer ${token}` },
      });

      if (res.ok) {
        const data = await res.json();
        if (data) {
          setBooking(data);
          setOperation("view");
        } else {
          alert("No booking data found.");
          setBooking(null);
        }
      } else {
        alert("Booking not found!");
        setBooking(null);
      }
    } catch (err) {
      alert("Error fetching booking: " + err.message);
    } finally {
      setLoading(false);
    }
  };

  const cancelBooking = async () => {
    if (!bookingRef.trim()) {
      alert("Please enter booking reference to cancel!");
      return;
    }

    const res = await fetch(`http://localhost:8082/bookings/${bookingRef}`, {
      method: "DELETE",
      headers: { Authorization: `Bearer ${token}` },
    });
    alert(await res.text());
    setBooking(null);
  };

  return (
    <div className="booking-page">
      <div className="booking-actions">
        <button className="booking-button booking-button-primary" onClick={() => setOperation("book")}>
          + New Booking
        </button>
        <button className="booking-button" onClick={() => setOperation("get")}>
          Search Booking
        </button>
        <button className="booking-button booking-button-cancel" onClick={() => setOperation("cancel")}>
          Cancel Booking
        </button>
      </div>

      {operation === "book" && (
        <div className="booking-card">
          <h3 className="booking-section-title">✈️ Book a Flight</h3>
          <div className="booking-form-grid">
            <div className="booking-input-group">
              <label className="booking-input-label">Flight Number</label>
              <input className="booking-input" name="flightNumber" placeholder="e.g., FL123" onChange={handleChange} />
            </div>
            <div className="booking-input-group">
              <label className="booking-input-label">Passenger Name</label>
              <input className="booking-input" name="passengerName" placeholder="Full Name" value={bookingData.passengerName} onChange={handleChange} readOnly style={{background: '#f1f5f9', cursor: 'not-allowed'}} />
            </div>
            <div className="booking-input-group">
              <label className="booking-input-label">Email Address</label>
              <input className="booking-input" name="email" placeholder="email@example.com" onChange={handleChange} />
            </div>
            <div className="booking-input-group">
              <label className="booking-input-label">Phone Number</label>
              <input className="booking-input" name="phone" placeholder="10 digits" onChange={handleChange} />
            </div>
            <div className="booking-input-group">
              <label className="booking-input-label">Number of Seats</label>
              <input className="booking-input" type="number" name="seatCount" placeholder="1" onChange={handleChange} />
            </div>
          </div>
          <button className="booking-button booking-button-confirm" onClick={addBooking}>
            {loading ? "Processing..." : "Confirm & Pay 💳"}
          </button>
        </div>
      )}

      {operation === "get" && (
        <div className="booking-card">
          <h3 className="booking-section-title">🔍 Search Booking</h3>
          <div className="booking-input-group">
            <label className="booking-input-label">Booking Reference</label>
            <input
              className="booking-input"
              placeholder="e.g., BOOK-12345678"
              value={bookingRef}
              onChange={(e) => setBookingRef(e.target.value)}
            />
          </div>
          <button className="booking-button booking-button-primary" onClick={getBooking} style={{width: '100%', marginTop: '10px'}}>
            {loading ? "Searching..." : "Search Booking"}
          </button>
        </div>
      )}

      {operation === "cancel" && (
        <div className="booking-card">
          <h3 className="booking-section-title-cancel">❌ Cancel Booking</h3>
          <div className="booking-input-group">
            <label className="booking-input-label">Booking Reference</label>
            <input
              className="booking-input"
              placeholder="e.g., BOOK-12345678"
              value={bookingRef}
              onChange={(e) => setBookingRef(e.target.value)}
            />
          </div>
          <button className="booking-button booking-button-cancel" onClick={cancelBooking} style={{width: '100%', marginTop: '10px'}}>
            Cancel Booking
          </button>
        </div>
      )}

      {operation === "view" && booking && (
        <div className="booking-card">
          <h3 className="booking-details-title">📋 Booking Details</h3>
          <div className="booking-table-container">
            <table className="booking-table">
            <thead>
              <tr>
                <th>Booking Ref</th>
                <th>Flight No</th>
                <th>Passenger</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Status</th>
                <th>Booking Date</th>
                <th>Fare</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>{booking.bookingReference}</td>
                <td>{booking.flightNumber}</td>
                <td>{booking.passengerName}</td>
                <td>{booking.email}</td>
                <td>{booking.phone}</td>
                <td className="booking-status">{booking.status}</td>
                <td>{new Date(booking.bookingDate).toLocaleString()}</td>
                <td className="booking-fare">{booking.fareAmount}</td>
              </tr>
            </tbody>
          </table>
          </div>
          {booking.status === "PENDING" && (
            <button 
              className="booking-button booking-button-primary" 
              onClick={async () => {
                try {
                  const res = await fetch(`http://localhost:8082/bookings/${booking.bookingReference}`, {
                    headers: { Authorization: `Bearer ${token}` },
                  });
                  if (res.ok) {
                    const data = await res.json();
                    if (data.sessionUrl) {
                      localStorage.setItem("latestBookingRef", booking.bookingReference);
                      window.open(data.sessionUrl, "_blank");
                    } else {
                      alert("Payment session not found!");
                    }
                  }
                } catch (err) {
                  alert("Error: " + err.message);
                }
              }}
              style={{width: '100%', marginTop: '20px'}}
            >
              💳 Complete Payment
            </button>
          )}
        </div>
      )}
    </div>
  );
}
