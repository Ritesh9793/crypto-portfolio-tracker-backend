import { BrowserRouter, Routes, Route } from "react-router-dom";

import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import Holdings from "./pages/Holdings";
import Trades from "./pages/Trades";
import Pricing from "./pages/Pricing";
import RiskAlerts from "./pages/RiskAlerts";
import PnLReports from "./pages/PnLReports";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/register" element={<Register />} />

        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/holdings" element={<Holdings />} />
        <Route path="/trades" element={<Trades />} />
        <Route path="/pricing" element={<Pricing />} />
        <Route path="/risk-alerts" element={<RiskAlerts />} />

        {/* FIXED ROUTE */}
        <Route path="/pnl-reports" element={<PnLReports />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
