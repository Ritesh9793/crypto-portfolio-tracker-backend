import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../api/axios";

export default function Login() {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = async () => {
    try {
      const res = await api.post("/auth/login", { email, password });

      if (res.data.token) {
        localStorage.setItem("token", res.data.token);
        localStorage.setItem("userEmail", res.data.email);
        localStorage.setItem("userName", res.data.name);
        navigate("/dashboard");
      } else {
        alert("Login failed — No token received!");
      }
    } catch (error) {
      alert("Invalid credentials");
    }
  };

  return (
    <div className="min-h-screen flex justify-center items-center bg-gradient-to-br from-black via-gray-900 to-black relative overflow-hidden">

      {/* Background Glow */}
      <div className="absolute w-[600px] h-[600px] bg-purple-600/30 blur-[150px] rounded-full -top-40 -left-40"></div>
      <div className="absolute w-[500px] h-[500px] bg-blue-600/30 blur-[150px] rounded-full bottom-0 right-0"></div>

      {/* Card */}
      <div className="relative z-10 w-[380px] bg-white/10 border border-white/20 backdrop-blur-2xl rounded-3xl p-10 shadow-2xl
                      hover:shadow-purple-700/40 transition-all duration-500">

        <h1 className="text-4xl font-bold text-center text-white mb-8 tracking-wide drop-shadow-lg">
          Welcome Back
        </h1>

        {/* Inputs */}
        <div className="space-y-4">
          <input
            type="email"
            placeholder="Email"
            onChange={(e) => setEmail(e.target.value)}
            className="w-full p-3 rounded-xl bg-white/10 text-white placeholder-gray-300 border border-white/20
                       focus:ring-2 focus:ring-purple-500 transition"
          />

          <input
            type="password"
            placeholder="Password"
            onChange={(e) => setPassword(e.target.value)}
            className="w-full p-3 rounded-xl bg-white/10 text-white placeholder-gray-300 border border-white/20
                       focus:ring-2 focus:ring-purple-500 transition"
          />
        </div>

        {/* Login Button */}
        <button
          onClick={handleLogin}
          className="w-full mt-6 py-3 rounded-xl bg-gradient-to-r from-purple-600 to-blue-600 
                     text-white font-semibold text-lg shadow-lg hover:scale-[1.03] active:scale-[0.97] transition-all"
        >
          Login
        </button>

        {/* Footer */}
        <p className="text-gray-300 text-center mt-6">
          Don’t have an account?
          <Link to="/register" className="text-purple-400 ml-2 hover:underline">
            Create one
          </Link>
        </p>
      </div>
    </div>
  );
}
