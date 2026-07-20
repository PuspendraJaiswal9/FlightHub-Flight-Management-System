import React from "react";
import "./HomePage.css";

export default function HomePage({ onGetStarted }) {
  return (
    <div className="landing-container">
      <nav className="landing-nav">
        <div className="logo">✈️ FlightHub</div>
        <button className="nav-btn" onClick={onGetStarted}>Sign In</button>
      </nav>

      <div className="hero-section">
        <div className="hero-content">
          <h1 className="hero-title">Book Your Flight in Minutes</h1>
          <p className="hero-subtitle">Complete flight management system with real-time booking, fare management, and secure payments powered by Stripe</p>
          <button className="cta-button" onClick={onGetStarted}>Get Started →</button>
          <div className="hero-features">
            <span>✓ Real-time Availability</span>
            <span>✓ Instant Confirmation</span>
            <span>✓ Secure Payment</span>
          </div>
        </div>
        <div className="hero-image">
          <img src="https://images.unsplash.com/photo-1436491865332-7a61a109cc05?w=800&q=80" alt="Flight" />
        </div>
      </div>

      <div className="services-section">
        <h2 className="section-title">Our Services</h2>
        <div className="services-grid">
          <div className="service-card">
            <div className="service-icon">🛫</div>
            <h3>Flight Management</h3>
            <p>Search and manage flights across 50+ destinations with real-time seat availability tracking</p>
            <ul>
              <li>Add & Update Flights</li>
              <li>Track Available Seats</li>
              <li>Flight Status Updates</li>
            </ul>
          </div>
          <div className="service-card">
            <div className="service-icon">💳</div>
            <h3>Smart Booking</h3>
            <p>Book tickets instantly with automated fare calculation and secure Stripe payment integration</p>
            <ul>
              <li>Instant Booking Confirmation</li>
              <li>Unique Booking Reference</li>
              <li>Email Notifications</li>
            </ul>
          </div>
          <div className="service-card">
            <div className="service-icon">💰</div>
            <h3>Fare Management</h3>
            <p>Dynamic pricing with base fare, taxes, and discount calculations for transparent pricing</p>
            <ul>
              <li>Base Fare + Tax Calculation</li>
              <li>Discount Management</li>
              <li>Real-time Price Updates</li>
            </ul>
          </div>
        </div>
      </div>

      <div className="how-it-works">
        <h2 className="section-title">How It Works</h2>
        <div className="steps-grid">
          <div className="step">
            <div className="step-number">1</div>
            <h3>Search Flights</h3>
            <p>Browse available flights by number or view all flights</p>
          </div>
          <div className="step">
            <div className="step-number">2</div>
            <h3>Select & Book</h3>
            <p>Choose your flight and enter passenger details</p>
          </div>
          <div className="step">
            <div className="step-number">3</div>
            <h3>Secure Payment</h3>
            <p>Pay securely via Stripe payment gateway</p>
          </div>
          <div className="step">
            <div className="step-number">4</div>
            <h3>Get Confirmation</h3>
            <p>Receive booking reference and confirmation</p>
          </div>
        </div>
      </div>

      <div className="stats-section">
        <div className="stat-item">
          <h2>500+</h2>
          <p>Daily Flights</p>
        </div>
        <div className="stat-item">
          <h2>50+</h2>
          <p>Destinations</p>
        </div>
        <div className="stat-item">
          <h2>200</h2>
          <p>Max Seats/Flight</p>
        </div>
        <div className="stat-item">
          <h2>24/7</h2>
          <p>Support</p>
        </div>
      </div>

      <div className="cta-section">
        <h2>Ready to Fly?</h2>
        <p>Join thousands of travelers who trust FlightHub for their journey</p>
        <button className="cta-button-large" onClick={onGetStarted}>Start Booking Now</button>
      </div>

      <footer className="landing-footer">
        <p>© 2025 FlightHub - Microservices Architecture | Powered by Spring Boot & React</p>
      </footer>
    </div>
  );
}
