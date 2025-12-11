import { useState, useEffect } from "react";
import DashboardLayout from "../layout/DashboardLayout";
import api from "../api/axios";

export default function Holdings() {
  const [holdings, setHoldings] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editing, setEditing] = useState(null);
  const [showDeletePopup, setShowDeletePopup] = useState(null);

  const [formData, setFormData] = useState({
    asset: "",
    symbol: "",
    quantity: "",
    price: "",
  });

  // RESET FORM WHEN MODAL OPENS
  const openAddModal = () => {
    setEditing(null);
    setFormData({
      asset: "",
      symbol: "",
      quantity: "",
      price: "",
    });
    setIsModalOpen(true);
  };

  const fetchHoldings = async () => {
    try {
      const res = await api.get("/holdings");
      setHoldings(res.data);
    } catch (err) {
      console.log("Error loading holdings", err);
    }
  };

  useEffect(() => {
    fetchHoldings();
  }, []);

  const handleSave = async () => {
    try {
      if (editing) {
        await api.put(`/holdings/${editing.id}`, formData);
      } else {
        await api.post("/holdings", formData);
      }

      setIsModalOpen(false);
      fetchHoldings();
    } catch (err) {
      console.log("Error saving holding", err);
    }
  };

  const handleDelete = async () => {
    try {
      await api.delete(`/holdings/${showDeletePopup.id}`);
      setShowDeletePopup(null);
      fetchHoldings();
    } catch (err) {
      console.log("Delete error", err);
    }
  };

  return (
    <DashboardLayout>
      <div className="p-10 text-white min-h-screen cyberpunk-bg">

        {/* Title */}
        <div className="flex justify-between items-center mb-10">
          <h1 className="text-4xl font-bold tracking-wide text-white">
  Holdings Overview
</h1>


          <button
            onClick={openAddModal}
            className="px-6 py-3 rounded-xl font-semibold neon-button"
          >
            + Add Holding
          </button>
        </div>

        {/* Table */}
        <div className="glass-card p-6 rounded-2xl border border-white/10 shadow-xl">
          <table className="w-full text-left">
            <thead>
              <tr className="text-gray-300 border-b border-white/10 uppercase text-sm">
                <th className="pb-4">Asset</th>
                <th className="pb-4">Symbol</th>
                <th className="pb-4">Quantity</th>
                <th className="pb-4">Price</th>
                <th className="px-6 py-4 text-sm font-semibold text-gray-400 text-right">
  ACTIONS
</th>

              </tr>
            </thead>

            <tbody>
              {holdings.map((h) => (
                <tr
                  key={h.id}
                  className="border-b border-white/5 hover:bg-white/5 transition-all group"
                >
                  <td className="py-4 font-semibold">{h.asset}</td>
                  <td>{h.symbol}</td>
                  <td>{h.quantity}</td>
                  <td className="text-green-400">${h.price}</td>

                  {/* Action buttons slide in */}
                  <td className="text-right opacity-0 group-hover:opacity-100 transition-all">
                    <button
                      onClick={() => {
                        setEditing(h);
                        setFormData(h);
                        setIsModalOpen(true);
                      }}
                      className="px-4 py-2 text-sm rounded-lg edit-btn"
                    >
                      Edit
                    </button>

                    <button
                      onClick={() => setShowDeletePopup(h)}
                      className="px-4 py-2 text-sm rounded-lg delete-btn"
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {/* MODAL */}
        {isModalOpen && (
          <div className="modal-overlay">
            <div className="modal-box">
              <h2 className="text-2xl font-bold mb-6 neon-text">
                {editing ? "Edit Holding" : "Add Holding"}
              </h2>

              {/* FORM */}
              <div className="flex flex-col gap-4">

                <input
                  className="cyber-input"
                  placeholder="Asset name"
                  value={formData.asset}
                  onChange={(e) => setFormData({ ...formData, asset: e.target.value })}
                />

                <input
                  className="cyber-input"
                  placeholder="Symbol"
                  value={formData.symbol}
                  onChange={(e) => setFormData({ ...formData, symbol: e.target.value })}
                />

                <input
                  className="cyber-input"
                  placeholder="Quantity"
                  type="number"
                  value={formData.quantity}
                  onChange={(e) => setFormData({ ...formData, quantity: e.target.value })}
                />

                <input
                  className="cyber-input"
                  placeholder="Price"
                  type="number"
                  value={formData.price}
                  onChange={(e) => setFormData({ ...formData, price: e.target.value })}
                />
              </div>

              <div className="flex justify-end gap-4 mt-8">
                <button
                  onClick={() => setIsModalOpen(false)}
                  className="cancel-btn"
                >
                  Cancel
                </button>

                <button
                  onClick={handleSave}
                  className="save-btn"
                >
                  Save
                </button>
              </div>
            </div>
          </div>
        )}

        {/* DELETE POPUP */}
        {showDeletePopup && (
          <div className="modal-overlay">
            <div className="delete-box">
              <h3 className="text-xl font-semibold mb-4">Delete Holding?</h3>
              <p className="text-gray-300 mb-6">
                Are you sure you want to delete <b>{showDeletePopup.asset}</b>?
              </p>

              <div className="flex justify-end gap-4">
                <button className="cancel-btn" onClick={() => setShowDeletePopup(null)}>
                  Cancel
                </button>

                <button className="delete-btn px-6 py-2" onClick={handleDelete}>
                  Delete
                </button>
              </div>
            </div>
          </div>
        )}

      </div>
    </DashboardLayout>
  );
}
