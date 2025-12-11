import DashboardLayout from "../layout/DashboardLayout";

// Recharts
import {
  LineChart,
  Line,
  PieChart,
  Pie,
  Cell,
  Tooltip,
  ResponsiveContainer,
  XAxis,
  YAxis,
  Legend,
} from "recharts";

export default function Dashboard() {
  // Dummy Line Chart Data
  const lineChartData = [
    { date: "Jan", value: 8000 },
    { date: "Feb", value: 9500 },
    { date: "Mar", value: 10200 },
    { date: "Apr", value: 12000 },
    { date: "May", value: 14000 },
  ];

  // Dummy Pie Chart Data
  const holdingsDistribution = [
    { name: "Bitcoin", value: 45 },
    { name: "Ethereum", value: 30 },
    { name: "Solana", value: 15 },
    { name: "Others", value: 10 },
  ];

  const COLORS = ["#3b82f6", "#8b5cf6", "#22c55e", "#f59e0b"];

  return (
    <DashboardLayout>
      <div className="p-6">

        {/* Page Title */}
        <h1 className="text-3xl font-bold text-white mb-6 drop-shadow-lg">
          Dashboard Overview
        </h1>

        {/* Stats Cards */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">

          <div className="bg-white/10 backdrop-blur-xl border border-white/20 
            rounded-2xl p-6 shadow-xl hover:scale-[1.02] transition-all">
            <h2 className="text-gray-300">Total Portfolio Value</h2>
            <p className="text-4xl font-bold text-white mt-2">$14,000</p>
          </div>

          <div className="bg-white/10 backdrop-blur-xl border border-white/20 
            rounded-2xl p-6 shadow-xl hover:scale-[1.02] transition-all">
            <h2 className="text-gray-300">Today's P/L</h2>
            <p className="text-4xl font-bold text-green-400 mt-2">+ $525</p>
          </div>

          <div className="bg-white/10 backdrop-blur-xl border border-white/20 
            rounded-2xl p-6 shadow-xl hover:scale-[1.02] transition-all">
            <h2 className="text-gray-300">Active Holdings</h2>
            <p className="text-4xl font-bold text-purple-400 mt-2">8</p>
          </div>

        </div>

        {/* Charts Section */}
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-6 mt-10">

          {/* Line Chart */}
          <div className="bg-white/10 p-6 rounded-2xl border border-white/20 backdrop-blur-xl shadow-lg">
            <h2 className="text-xl text-white font-semibold mb-4">Portfolio Value Trend</h2>

            <div className="w-full h-64">
              <ResponsiveContainer width="100%" height="100%">
                <LineChart data={lineChartData}>
                  <XAxis dataKey="date" stroke="#ccc" />
                  <YAxis stroke="#ccc" />
                  <Tooltip />
                  <Line
                    type="monotone"
                    dataKey="value"
                    stroke="#3b82f6"
                    strokeWidth={3}
                    dot={{ r: 4 }}
                  />
                </LineChart>
              </ResponsiveContainer>
            </div>
          </div>

          {/* Pie Chart */}
          <div className="bg-white/10 p-6 rounded-2xl border border-white/20 backdrop-blur-xl shadow-lg">
            <h2 className="text-xl text-white font-semibold mb-4">Holdings Distribution</h2>

            <div className="w-full h-64 flex items-center justify-center">
              <ResponsiveContainer width="100%" height="100%">
                <PieChart>
                  <Pie
                    data={holdingsDistribution}
                    dataKey="value"
                    outerRadius={110}
                    label={({ name, percent }) =>
                      `${name} ${(percent * 100).toFixed(0)}%`
                    }
                  >
                    {holdingsDistribution.map((entry, index) => (
                      <Cell key={index} fill={COLORS[index % COLORS.length]} />
                    ))}
                  </Pie>
                  <Legend />
                  <Tooltip />
                </PieChart>
              </ResponsiveContainer>
            </div>
          </div>

        </div>

        {/* Recent Activity */}
        <h2 className="text-2xl text-white font-semibold mt-10 mb-4">
          Recent Activity
        </h2>

        <div className="bg-white/10 backdrop-blur-xl border border-white/20 rounded-2xl p-6">
          <table className="w-full text-left text-gray-200">
            <thead>
              <tr className="border-b border-white/20">
                <th className="py-3">Asset</th>
                <th className="py-3">Type</th>
                <th className="py-3">Amount</th>
                <th className="py-3">Date</th>
              </tr>
            </thead>

            <tbody>
              <tr className="border-b border-white/10 hover:bg-white/5">
                <td className="py-3">Bitcoin (BTC)</td>
                <td>Buy</td>
                <td>0.020 BTC</td>
                <td>2025-12-09</td>
              </tr>

              <tr className="border-b border-white/10 hover:bg-white/5">
                <td className="py-3">Ethereum (ETH)</td>
                <td>Sell</td>
                <td>0.50 ETH</td>
                <td>2025-12-08</td>
              </tr>

              <tr className="hover:bg-white/5">
                <td className="py-3">Solana (SOL)</td>
                <td>Buy</td>
                <td>10 SOL</td>
                <td>2025-12-07</td>
              </tr>
            </tbody>
          </table>
        </div>

      </div>
    </DashboardLayout>
  );
}
