import React, { useState } from "react";
import { BrowserRouter as Router, Routes, Route, Link, useLocation } from "react-router-dom";
import HomePage from "./components/HomePage";
import DashboardHome from "./components/Dashboard";
import FlightOperation from "./components/FlightOperation";
import FareOperation from "./components/FareOperation";
import BookingOperation from "./components/BookingOperation";
import MyBookings from "./components/MyBookings";
import PaymentSuccess from "./components/PaymentSuccess";
import PaymentCancel from "./components/PaymentCancel";
import AuthPage from "./auth/AuthPage";
import "./App.css";
import "./auth/AuthPage.css";

function Dashboard({ handleLogout }) {
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const location = useLocation();

  const getUserInfo = () => {
    const token = localStorage.getItem('token');
    const roles = JSON.parse(localStorage.getItem('roles') || '[]');
    
    let username = 'User';
    let role = 'User';
    
    if (token) {
      try {
        const decoded = JSON.parse(atob(token.split('.')[1]));
        username = decoded.sub || 'User';
      } catch (e) {
        console.error('Token decode error:', e);
      }
    }
    
    if (roles.includes('ROLE_ADMIN')) {
      role = 'Admin';
    } else if (roles.includes('ROLE_USER')) {
      role = 'User';
    }
    
    return { username, role };
  };

  const getPageTitle = () => {
    switch(location.pathname) {
      case '/': return 'Dashboard';
      case '/flight': return 'Flight Scheduler';
      case '/fare': return 'Pricing & Fares';
      case '/booking': return 'Reservations';
      case '/my-bookings': return 'My Bookings';
      default: return 'Dashboard';
    }
  };

  const userInfo = getUserInfo();

  return (
    <div className="dashboard-layout">
      <button className="mobile-menu-btn" onClick={() => setSidebarOpen(!sidebarOpen)}>
        ☰
      </button>

      <aside className={`sidebar ${sidebarOpen ? 'open' : ''}`}>
        <div className="sidebar-header">
          <div className="sidebar-logo">✈️ FlightHub</div>
        </div>

        <nav className="sidebar-nav">
          <Link to="/" className={`nav-item ${location.pathname === '/' ? 'active' : ''}`} onClick={() => setSidebarOpen(false)}>
            <span className="nav-icon">🏠</span>
            <span>Dashboard</span>
          </Link>
          <Link to="/flight" className={`nav-item ${location.pathname === '/flight' ? 'active' : ''}`} onClick={() => setSidebarOpen(false)}>
            <span className="nav-icon">✈️</span>
            <span>Flight Scheduler</span>
          </Link>
          <Link to="/fare" className={`nav-item ${location.pathname === '/fare' ? 'active' : ''}`} onClick={() => setSidebarOpen(false)}>
            <span className="nav-icon">💰</span>
            <span>Pricing & Fares</span>
          </Link>
          <Link to="/booking" className={`nav-item ${location.pathname === '/booking' ? 'active' : ''}`} onClick={() => setSidebarOpen(false)}>
            <span className="nav-icon">🎫</span>
            <span>Reservations</span>
          </Link>
          <Link to="/my-bookings" className={`nav-item ${location.pathname === '/my-bookings' ? 'active' : ''}`} onClick={() => setSidebarOpen(false)}>
            <span className="nav-icon">📋</span>
            <span>My Bookings</span>
          </Link>
        </nav>

        <div className="sidebar-footer">
          <button className="logout-btn" onClick={handleLogout}>
            <span>🚪</span>
            <span>Logout</span>
          </button>
        </div>
      </aside>

      <main className="main-content">
        <div className="top-bar">
          <h1 className="page-title">{getPageTitle()}</h1>
          <div className="user-info">
            <div className="user-avatar">{userInfo.username.charAt(0).toUpperCase()}</div>
            <div className="user-details">
              <div className="user-name">{userInfo.username}</div>
              <div className="user-role">{userInfo.role}</div>
            </div>
          </div>
        </div>

        <div className="content-area">
          <Routes>
            <Route path="/" element={<DashboardHome />} />
            <Route path="/flight" element={<FlightOperation />} />
            <Route path="/fare" element={<FareOperation />} />
            <Route path="/booking" element={<BookingOperation />} />
            <Route path="/my-bookings" element={<MyBookings />} />
            <Route path="/payment-success" element={<PaymentSuccess />} />
            <Route path="/payment-cancel" element={<PaymentCancel />} />
          </Routes>
        </div>
      </main>
    </div>
  );
}

function App() {
  const [showLanding, setShowLanding] = useState(() => {
    const token = localStorage.getItem('token');
    return !token;
  });
  const [isAuthenticated, setIsAuthenticated] = useState(() => {
    const token = localStorage.getItem('token');
    return !!token;
  });

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("roles");
    setIsAuthenticated(false);
    setShowLanding(true);
  };

  if (showLanding && !isAuthenticated) {
    return <HomePage onGetStarted={() => setShowLanding(false)} />;
  }

  if (!isAuthenticated) {
    return <AuthPage onLoginSuccess={() => setIsAuthenticated(true)} />;
  }

  return (
    <Router>
      <Dashboard handleLogout={handleLogout} />
    </Router>
  );
}

export default App;
