import React from "react";
import { useNavigate } from "react-router-dom";
import "./PaymentCancel.css";

export default function PaymentCancel() {
  const navigate = useNavigate();

  return (
    <div className="payment-cancel-container">
      <h1 className="payment-cancel-title">❌ Payment Cancelled!</h1>
      <p>Your payment was not completed. You can try again.</p>
      <button className="payment-cancel-button" onClick={() => navigate("/")}>
        Back to Home
      </button>
    </div>
  );
}
