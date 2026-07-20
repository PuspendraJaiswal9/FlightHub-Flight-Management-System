import React, { useState } from "react";
import "./FlightOperation.css"; 

export default function FlightOperation() {
  const [operation, setOperation] = useState("");
  const [flightData, setFlightData] = useState({
    flightNumber: "",
    flightName: "",
    departureCity: "",
    arrivalCity: "",
    departureDate: "",
    departureTime: "",
    arrivalDate: "",
    arrivalTime: "",
    totalSeats: "",
    availableSeats: "",
    status: "",
  });

  const [idOrNumber, setIdOrNumber] = useState("");
  const [searchData, setSearchData] = useState({
    source: "",
    destination: ""
  });
  const [result, setResult] = useState(null);

  const token = localStorage.getItem("token");
  const roles = JSON.parse(localStorage.getItem("roles") || "[]");
  const isAdmin = roles.includes("ROLE_ADMIN");
  const isUser = roles.includes("ROLE_USER");

  const handleChange = (e) => {
    setFlightData({ ...flightData, [e.target.name]: e.target.value });
  };

  const addFlight = async () => {
    const res = await fetch("http://localhost:8081/flight/add", {
      method: "POST",
      headers: { 
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(flightData),
    });
    const data = await res.json();
    setResult([data]);
    alert("Flight Added Successfully!");
    setOperation("");
  };

  const updateFlight = async () => {
    const res = await fetch(`http://localhost:8081/flight/${idOrNumber}`, {
      method: "PUT",
      headers: { 
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(flightData),
    });
    const data = await res.json();
    setResult([data]);
    alert("Flight Updated Successfully!");
    setOperation("");
  };

  const getAllFlights = async () => {
    const res = await fetch("http://localhost:8081/flight", {
      headers: { Authorization: `Bearer ${token}` },
    });
    const data = await res.json();
    setResult(data);
    setOperation("");
  };

  const getFlightById = async () => {
    const res = await fetch(`http://localhost:8081/flight/${idOrNumber}`, {
      headers: { Authorization: `Bearer ${token}` },
    });
    const data = await res.json();
    setResult([data]);
    setOperation("");
  };

  const getFlightByNumber = async () => {
    const res = await fetch(`http://localhost:8081/flight/number/${idOrNumber}`, {
      headers: { Authorization: `Bearer ${token}` },
    });
    const data = await res.json();
    setResult([data]);
    setOperation("");
  };

  const deleteFlight = async () => {
    const res = await fetch(`http://localhost:8081/flight/${idOrNumber}`, {
      method: "DELETE",
      headers: { Authorization: `Bearer ${token}` },
    });
    const msg = await res.text();
    setResult([{ message: msg }]);
    alert("Flight Deleted!");
    setOperation("");
  };

  const handleOperation = (op) => {
    setOperation(op);
    setResult(null);
    setIdOrNumber("");
    setSearchData({ source: "", destination: "" });
  };

  const searchByRoute = async () => {
    if (!searchData.source || !searchData.destination) {
      alert("Please enter both source and destination!");
      return;
    }

    try {
      const res = await fetch("http://localhost:8081/flight", {
        headers: { Authorization: `Bearer ${token}` },
      });
      const data = await res.json();
      
      const filtered = data.filter(flight => 
        flight.departureCity.toLowerCase().includes(searchData.source.toLowerCase()) &&
        flight.arrivalCity.toLowerCase().includes(searchData.destination.toLowerCase())
      );
      
      if (filtered.length === 0) {
        alert("No flights found for this route!");
      }
      setResult(filtered);
      setOperation("");
    } catch (err) {
      alert("Error searching flights: " + err.message);
    }
  };

  return (
    <div className="flight-page">
      <div className="flight-actions">
        {isAdmin && (
          <>
            <button className="flight-button flight-button-success" onClick={() => handleOperation("add")}>+ Add Flight</button>
            <button className="flight-button flight-button-primary" onClick={() => handleOperation("update")}>Edit Flight</button>
            <button className="flight-button flight-button-danger" onClick={() => handleOperation("delete")}>Delete Flight</button>
          </>
        )}

        {(isUser || isAdmin) && (
          <>
            <button className="flight-button" onClick={getAllFlights}>All Flights</button>
            <button className="flight-button" onClick={() => handleOperation("getByRoute")}>Search by Route</button>
            <button className="flight-button" onClick={() => handleOperation("getByNumber")}>Search by Number</button>
          </>
        )}
      </div>

      {["add","update","getByRoute","getByNumber","delete"].includes(operation) && (
        <div className="flight-section-box">
          <h3 className="flight-section-title">
            {operation === "add" ? "➕ Add New Flight" :
             operation === "update" ? "✏️ Update Flight" :
             operation === "delete" ? "🗑️ Delete Flight" :
             operation === "getByRoute" ? "🔍 Search by Route" : "🔍 Search Flight by Number"}
          </h3>

          {operation === "getByRoute" && (
            <>
              <div style={{marginBottom: '20px'}}>
                <label style={{display: 'block', marginBottom: '8px', color: '#334155', fontWeight: '600', fontSize: '0.9rem'}}>
                  From (Source City)
                </label>
                <input
                  className="flight-input"
                  placeholder="e.g., Delhi, Mumbai"
                  value={searchData.source}
                  onChange={(e) => setSearchData({...searchData, source: e.target.value})}
                />
              </div>
              <div style={{marginBottom: '20px'}}>
                <label style={{display: 'block', marginBottom: '8px', color: '#334155', fontWeight: '600', fontSize: '0.9rem'}}>
                  To (Destination City)
                </label>
                <input
                  className="flight-input"
                  placeholder="e.g., Bangalore, Kolkata"
                  value={searchData.destination}
                  onChange={(e) => setSearchData({...searchData, destination: e.target.value})}
                />
              </div>
            </>
          )}

          {(operation === "update" || operation === "delete" || operation === "getByNumber") && (
            <div style={{marginBottom: '20px'}}>
              <label style={{display: 'block', marginBottom: '8px', color: '#334155', fontWeight: '600', fontSize: '0.9rem'}}>
                {operation.includes("Number") ? "Flight Number" : "Flight ID"}
              </label>
              <input
                className="flight-input"
                placeholder={operation.includes("Number") ? "e.g., FL123" : "e.g., 1"}
                value={idOrNumber}
                onChange={(e) => setIdOrNumber(e.target.value)}
              />
            </div>
          )}

          {(operation === "add" || operation === "update") && (
            <>
              <input className="flight-input" name="flightNumber" placeholder="Flight Number" onChange={handleChange} />
              <input className="flight-input" name="flightName" placeholder="Flight Name" onChange={handleChange} />
              <input className="flight-input" name="departureCity" placeholder="Departure City" onChange={handleChange} />
              <input className="flight-input" name="arrivalCity" placeholder="Arrival City" onChange={handleChange} />
              <input className="flight-input" type="date" name="departureDate" onChange={handleChange} />
              <input className="flight-input" type="time" name="departureTime" onChange={handleChange} />
              <input className="flight-input" type="date" name="arrivalDate" onChange={handleChange} />
              <input className="flight-input" type="time" name="arrivalTime" onChange={handleChange} />
              <input className="flight-input" type="number" name="totalSeats" placeholder="Total Seats" onChange={handleChange} />
              <input className="flight-input" type="number" name="availableSeats" placeholder="Available Seats" onChange={handleChange} />
              <input className="flight-input" name="status" placeholder="Status" onChange={handleChange} />
            </>
          )}

          <div style={{display: 'flex', gap: '10px', justifyContent: 'flex-end', marginTop: '20px'}}>
            {operation === "add" && <button className="flight-button flight-button-success" onClick={addFlight}>Save Flight</button>}
            {operation === "update" && <button className="flight-button flight-button-primary" onClick={updateFlight}>Update Flight</button>}
            {operation === "delete" && <button className="flight-button flight-button-danger" onClick={deleteFlight}>Delete Flight</button>}
            {operation === "getByRoute" && <button className="flight-button flight-button-primary" onClick={searchByRoute}>Search Flights</button>}
            {operation === "getByNumber" && <button className="flight-button flight-button-primary" onClick={getFlightByNumber}>Search</button>}
            <button className="flight-button" onClick={() => setOperation("")}>Cancel</button>
          </div>
        </div>
      )}

      {result && result.length > 0 && (
        <div className="table-wrapper">
          <div className="table-header">
            <div className="table-title">📊 Flight Results</div>
            <div className="table-count">{result.length} {result.length === 1 ? 'Flight' : 'Flights'}</div>
          </div>
          <div className="flights-grid">
            {result.map((flight, i) => (
              <div key={i} className="flight-result-card">
                <div className="flight-card-header">
                  <div className="flight-number">{flight.flightNumber}</div>
                  <div className="flight-status-badge">{flight.status}</div>
                </div>
                <div className="flight-card-body">
                  <div className="flight-info-item">
                    <span className="info-label">Flight Name</span>
                    <span className="info-value">{flight.flightName}</span>
                  </div>
                  <div className="flight-route">
                    <div className="route-item">
                      <span className="route-label">From</span>
                      <span className="route-city">{flight.departureCity}</span>
                    </div>
                    <span className="route-arrow">→</span>
                    <div className="route-item">
                      <span className="route-label">To</span>
                      <span className="route-city">{flight.arrivalCity}</span>
                    </div>
                  </div>
                  <div className="flight-timing">
                    <div className="timing-item">
                      <span className="timing-label">Departure</span>
                      <span className="timing-value">{flight.departureDate} {flight.departureTime}</span>
                    </div>
                    <div className="timing-item">
                      <span className="timing-label">Arrival</span>
                      <span className="timing-value">{flight.arrivalDate} {flight.arrivalTime}</span>
                    </div>
                  </div>
                  <div className="flight-seats">
                    <div className="seats-item">
                      <span className="seats-label">Total Seats</span>
                      <span className="seats-value">{flight.totalSeats}</span>
                    </div>
                    <div className="seats-item">
                      <span className="seats-label">Available</span>
                      <span className="seats-value available">{flight.availableSeats}</span>
                    </div>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
}
