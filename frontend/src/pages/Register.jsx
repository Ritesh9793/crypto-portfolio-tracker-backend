import { useState } from "react";
import { Link } from "react-router-dom";
import api from "../api/axios";

export default function Register() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleRegister = async () => {
  try {
    await api.post("/auth/register", {
      name,
      email,
      password,
    });

    alert("Registration successful!");
    window.location.href = "/";
  } catch (err) {
    console.log(err);  // <--- IMPORTANT
    alert("Registration failed");
  }
};


  return (
    <div className="min-h-screen flex justify-center items-center relative overflow-hidden bg-black">
      
      <div className="absolute inset-0 bg-gradient-to-br from-blue-700/30 via-purple-600/20 to-black animate-pulse"></div>

      <div className="absolute w-80 h-80 bg-purple-600/20 rounded-full blur-3xl top-16 left-14 animate-spin-slow"></div>
      <div className="absolute w-96 h-96 bg-blue-600/20 rounded-full blur-3xl bottom-10 right-10 animate-bounce"></div>

      <div className="relative z-10 bg-white/10 backdrop-blur-xl border border-white/20 shadow-2xl p-10 rounded-3xl w-96">
        
        <h1 className="text-4xl font-bold text-white text-center mb-8 drop-shadow-lg">
          Create Account
        </h1>

        <input
          type="text"
          placeholder="Full Name"
          onChange={(e) => setName(e.target.value)}
          className="w-full mb-4 p-3 rounded-xl bg-white/10 text-white border border-white/30 
          placeholder-gray-300 focus:ring-2 focus:ring-purple-500 focus:outline-none"
        />

        <input
          type="email"
          placeholder="Email Address"
          onChange={(e) => setEmail(e.target.value)}
          className="w-full mb-4 p-3 rounded-xl bg-white/10 text-white border border-white/30 
          placeholder-gray-300 focus:ring-2 focus:ring-purple-500 focus:outline-none"
        />

        <input
          type="password"
          placeholder="Password"
          onChange={(e) => setPassword(e.target.value)}
          className="w-full mb-6 p-3 rounded-xl bg-white/10 text-white border border-white/30 
          placeholder-gray-300 focus:ring-2 focus:ring-purple-500 focus:outline-none"
        />

        <button
          onClick={handleRegister}
          className="w-full py-3 rounded-xl bg-gradient-to-r from-purple-600 to-blue-600 
          text-white font-semibold hover:scale-[1.03] active:scale-[0.98] transition-all shadow-lg 
          hover:shadow-blue-900/50"
        >
          Register
        </button>

        <p className="text-gray-300 text-center mt-6">
          Already have an account?{" "}
          <Link to="/" className="text-blue-400 hover:underline">
            Login
          </Link>
        </p>
      </div>
    </div>
  );
}
