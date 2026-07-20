import React, { useState } from "react";
import { jwtDecode } from "jwt-decode";
import "./FareOperation.css";

export default function FareOperation() {
  const [operation, setOperation] = useState("");
  const [fareData, setFareData] = useState({
    flightNumber: "",
    baseFare: "",
    fareTax: "",
    discount: "",
  });
  const [result, setResult] = useState([]);

  const token = localStorage.getItem("token");

  let role = null;
  if (token) {
    try {
      const decoded = jwtDecode(token);
      role = decoded.roles?.[0] || null;
    } catch (e) {
      console.error("Invalid Token", e);
    }
  }

  const handleChange = (e) =>
    setFareData({ ...fareData, [e.target.name]: e.target.value });

  const handleOperation = (op) => {
    setOperation(op);
    setResult([]);
    setFareData({
      flightNumber: "",
      baseFare: "",
      fareTax: "",
      discount: "",
    });
  };

  const addFare = async () => {
    try {
      const payload = {
        ...fareData,
        baseFare: Number(fareData.baseFare),
        fareTax: Number(fareData.fareTax),
        discount: Number(fareData.discount),
      };
      const res = await fetch("http://localhost:8083/fare/add", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(payload),
      });
      if (!res.ok) throw new Error(await res.text());
      await res.json();
      alert("Fare Added Successfully!");
      resetOperation();
    } catch (e) {
      alert("Add Fare failed: " + e.message);
    }
  };

  const updateFare = async () => {
    if (!fareData.flightNumber) {
      alert("Please enter Flight Number to update fare.");
      return;
    }
    try {
      const payload = {
        ...fareData,
        baseFare: Number(fareData.baseFare),
        fareTax: Number(fareData.fareTax),
        discount: Number(fareData.discount),
      };
      const res = await fetch(
        `http://localhost:8083/fare/number/${fareData.flightNumber}`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify(payload),
        }
      );
      if (!res.ok) throw new Error(await res.text());
      await res.json();
      alert("Fare Updated Successfully!");
      resetOperation();
    } catch (e) {
      alert("Update Fare failed: " + e.message);
    }
  };

  const getFare = async () => {
    if (!fareData.flightNumber) {
      alert("Please enter Flight Number to fetch fare.");
      return;
    }
    try {
      const res = await fetch(
        `http://localhost:8083/fare/${fareData.flightNumber}`,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      if (!res.ok) throw new Error(await res.text());
      const data = await res.json();

      console.log("Fetched Fare Data:", data);
      if (data) setResult(Array.isArray(data) ? data : [data]);
      else setResult([]);
    } catch (e) {
      alert("Get Fare failed: " + e.message);
    }
  };

  const deleteFare = async () => {
    if (!fareData.flightNumber) {
      alert("Please enter Flight Number to delete fare.");
      return;
    }
    if (!window.confirm("Are you sure you want to delete this fare?")) return;

    try {
      const res = await fetch(
        `http://localhost:8083/fare/${fareData.flightNumber}`,
        {
          method: "DELETE",
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      if (!res.ok) throw new Error(await res.text());
      await res.text();
      alert("Fare Deleted Successfully!");
      resetOperation();
    } catch (e) {
      alert("Delete Fare failed: " + e.message);
    }
  };

  const resetOperation = () => {
    setOperation("");
    setFareData({
      flightNumber: "",
      baseFare: "",
      fareTax: "",
      discount: "",
    });
  };

  const renderResult = () => {
    if (!result || result.length === 0) return null;

    const firstRow = result[0];
    if (!firstRow || typeof firstRow !== "object") return <p>No data found.</p>;

    const cols = Object.keys(firstRow);
    return (
      <div className="fare-table-wrapper">
        <div className="fare-table-header">
          <div className="fare-table-title">📊 Fare Results</div>
          <div className="fare-table-count">{result.length} {result.length === 1 ? 'Fare' : 'Fares'}</div>
        </div>
        <div className="fare-table-container">
          <table className="fare-table">
            <thead>
              <tr>
                {cols.map((key) => (
                  <th key={key}>
                    {key.toUpperCase()}
                  </th>
                ))}
              </tr>
            </thead>
            <tbody>
              {result.map((row, idx) => (
                <tr key={idx}>
                  {cols.map((key) => (
                    <td key={key}>
                      {String(row[key] ?? "")}
                    </td>
                  ))}
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    );
  };

  return (
    <div className="fare-container">
      <div className="fare-actions">
        {role === "ROLE_ADMIN" && (
          <>
            <button className="fare-button fare-button-success" onClick={() => handleOperation("add")}>
              + Add Fare
            </button>
            <button className="fare-button fare-button-primary" onClick={() => handleOperation("update")}>
              Edit Fare
            </button>
            <button className="fare-button fare-button-delete" onClick={() => handleOperation("delete")}>
              Delete Fare
            </button>
          </>
        )}
        <button className="fare-button" onClick={() => handleOperation("get")}>
          Search Fare
        </button>
      </div>

      {(operation === "add" || operation === "update") && (
        <div className="fare-card">
          <h3 className="fare-section-title">
            {operation === "add" ? "➕ Add New Fare" : "✏️ Update Fare"}
          </h3>
          <div className="fare-input-group">
            <label className="fare-input-label">Flight Number</label>
            <input
              className="fare-input"
              name="flightNumber"
              placeholder="e.g., FL123"
              value={fareData.flightNumber}
              onChange={handleChange}
            />
          </div>
          <div className="fare-input-group">
            <label className="fare-input-label">Base Fare</label>
            <input
              className="fare-input"
              type="number"
              name="baseFare"
              placeholder="e.g., 5000"
              value={fareData.baseFare}
              onChange={handleChange}
            />
          </div>
          <div className="fare-input-group">
            <label className="fare-input-label">Fare Tax</label>
            <input
              className="fare-input"
              type="number"
              name="fareTax"
              placeholder="e.g., 500"
              value={fareData.fareTax}
              onChange={handleChange}
            />
          </div>
          <div className="fare-input-group">
            <label className="fare-input-label">Discount</label>
            <input
              className="fare-input"
              type="number"
              name="discount"
              placeholder="e.g., 200"
              value={fareData.discount}
              onChange={handleChange}
            />
          </div>

          <div style={{display: 'flex', gap: '10px', marginTop: '20px'}}>
            <button className={`fare-button fare-button-submit ${operation === 'add' ? 'fare-button-success' : 'fare-button-primary'}`} onClick={operation === "add" ? addFare : updateFare}>
              {operation === "add" ? "Save Fare" : "Update Fare"}
            </button>
            <button className="fare-button" onClick={resetOperation}>Cancel</button>
          </div>
        </div>
      )}

      {(operation === "get" || operation === "delete") && (
        <div className="fare-card">
          <h3 className="fare-section-title">
            {operation === "get" ? "🔍 Search Fare" : "🗑️ Delete Fare"}
          </h3>
          <div className="fare-input-group">
            <label className="fare-input-label">Flight Number</label>
            <input
              className="fare-input"
              name="flightNumber"
              placeholder="e.g., FL123"
              value={fareData.flightNumber}
              onChange={handleChange}
            />
          </div>
          <div style={{display: 'flex', gap: '10px', marginTop: '20px'}}>
            <button
              className={`fare-button fare-button-submit ${operation === 'get' ? 'fare-button-primary' : 'fare-button-delete'}`}
              onClick={operation === "get" ? getFare : deleteFare}
            >
              {operation === "get" ? "Search" : "Delete Fare"}
            </button>
            <button className="fare-button" onClick={resetOperation}>Cancel</button>
          </div>
        </div>
      )}

      {renderResult()}
    </div>
  );
}
