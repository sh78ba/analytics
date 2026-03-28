export default function SummaryCard({ title, value, icon, color = "blue" }) {
  const styleClasses = {
    blue: "from-blue-400 to-blue-600 shadow-blue-200",
    purple: "from-purple-400 to-purple-600 shadow-purple-200",
    green: "from-green-400 to-green-600 shadow-green-200",
    orange: "from-orange-400 to-orange-600 shadow-orange-200",
  };

  return (
    <div
      className={`bg-gradient-to-br ${styleClasses[color]} p-8 rounded-2xl shadow-2xl text-white transform hover:scale-105 transition-transform duration-300`}
    >
      <div className="flex items-center justify-between">
        <div>
          <p className="text-sm font-semibold text-white/80 mb-3 uppercase tracking-wider">
            {title}
          </p>
          <p className="text-5xl font-bold">{value}</p>
        </div>
        {icon && <div className="text-7xl opacity-30 ml-4">{icon}</div>}
      </div>
      <div className="mt-4 h-1 bg-white/30 rounded-full"></div>
    </div>
  );
}
