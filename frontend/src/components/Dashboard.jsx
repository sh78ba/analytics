import { useState, useEffect } from "react";
import SummaryCard from "./SummaryCard";
import MetricsChart from "./MetricsChart";
import Insights from "./Insights";

export default function Dashboard() {
  const [metrics, setMetrics] = useState({});
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchMetrics = async () => {
      try {
        setLoading(true);
        setError(null);
        const response = await fetch("http://localhost:8080/metrics/summary", {
          method: "GET",
          headers: { "Content-Type": "application/json" },
        });
        if (response.ok) {
          const data = await response.json();
          console.log("Metrics fetched successfully:", data);
          setMetrics(data);
        } else {
          console.error("Failed to fetch metrics:", response.status);
          setError("Failed to fetch metrics from backend");
        }
      } catch (err) {
        console.error("Error fetching metrics:", err);
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchMetrics();
  }, []);

  // Default placeholder data
  const placeholderMetrics = {
    total_events: 1250,
    active_users: 342,
    events_per_minute: 18,
    average_session_duration: 4.2,
  };

  const displayMetrics =
    Object.keys(metrics).length > 0 ? metrics : placeholderMetrics;

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-900 via-purple-900 to-slate-900">
      {/* Header */}
      <header className="bg-gradient-to-r from-blue-600 to-purple-600 shadow-2xl">
        <div className="max-w-7xl mx-auto px-6 py-10">
          <h1 className="text-5xl font-black text-white mb-2">
            📊 Analytics Dashboard
          </h1>
          <p className="text-blue-50 text-lg">
            Real-time event analytics and AI-powered insights
          </p>
        </div>
      </header>

      {/* Main Content */}
      <main className="max-w-7xl mx-auto px-6 py-12">
        {/* Summary Cards */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-12">
          <SummaryCard
            title="Total Events"
            value={displayMetrics.total_events || 0}
            icon="📊"
            color="blue"
          />
          <SummaryCard
            title="Active Users"
            value={displayMetrics.active_users || 0}
            icon="👥"
            color="purple"
          />
          <SummaryCard
            title="Events/Min"
            value={displayMetrics.events_per_minute || 0}
            icon="⚡"
            color="green"
          />
          <SummaryCard
            title="Avg Duration"
            value={`${displayMetrics.average_session_duration || 0}m`}
            icon="⏱️"
            color="orange"
          />
        </div>

        {/* Charts and Insights Grid */}
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
          <div className="lg:col-span-2">
            <MetricsChart data={displayMetrics} />
          </div>
          <div>
            <Insights />
          </div>
        </div>
      </main>

      {/* Footer */}
      <footer className="bg-gradient-to-r from-slate-900 to-purple-900 text-gray-300 mt-16 border-t border-purple-800/50">
        <div className="max-w-7xl mx-auto px-6 py-12 text-center">
          <p className="text-sm">
            ✨ &copy; 2026 AI Analytics. Powered by Spring Boot & React. All
            rights reserved.
          </p>
        </div>
      </footer>
    </div>
  );
}
