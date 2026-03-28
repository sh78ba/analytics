export default function MetricsChart({ data = {} }) {
  const metrics = Object.entries(data).map(([key, value]) => ({
    label: key.replace(/_/g, " ").toUpperCase(),
    value: value,
    percentage: (value / Math.max(...Object.values(data), 1)) * 100,
  }));

  return (
    <div className="bg-white rounded-2xl shadow-2xl p-8 border border-gray-100">
      <h3 className="text-2xl font-bold text-gray-900 mb-8">
        📊 Metrics Overview
      </h3>

      {metrics.length === 0 ? (
        <p className="text-gray-400 text-center py-12">
          No metrics data available
        </p>
      ) : (
        <div className="space-y-6">
          {metrics.map((metric, idx) => (
            <div key={idx} className="group">
              <div className="flex justify-between items-center mb-3">
                <span className="text-sm font-bold text-gray-600 group-hover:text-purple-600 transition">
                  {metric.label}
                </span>
                <span className="text-lg font-bold text-purple-600 bg-purple-50 px-3 py-1 rounded-lg">
                  {metric.value}
                </span>
              </div>
              <div className="w-full bg-gradient-to-r from-gray-100 to-gray-50 rounded-full h-3 overflow-hidden shadow-inner">
                <div
                  className="bg-gradient-to-r from-blue-500 via-purple-500 to-pink-500 h-3 rounded-full shadow-lg transition-all duration-500"
                  style={{ width: `${metric.percentage}%` }}
                ></div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
