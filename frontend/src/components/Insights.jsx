import { useState } from "react";

export default function Insights() {
  const [insights, setInsights] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [rawResponse, setRawResponse] = useState(null);

  const fetchInsights = async () => {
    setLoading(true);
    setError(null);
    setInsights(null);
    try {
      const response = await fetch("http://localhost:8080/insights");
      if (!response.ok) throw new Error("Failed to fetch insights");
      const data = await response.json();
      setRawResponse(data);

      // Extract the actual insight message from OpenAI response
      if (data.choices && data.choices[0] && data.choices[0].message) {
        const content = data.choices[0].message.content;
        setInsights(content);
      } else if (typeof data === "string") {
        setInsights(data);
      } else {
        setInsights(JSON.stringify(data, null, 2));
      }
    } catch (err) {
      console.error("Error fetching insights:", err);
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="bg-gradient-to-br from-white to-gray-50 rounded-2xl shadow-2xl p-8 border border-gray-100 flex flex-col h-full">
      <div className="mb-6">
        <h3 className="text-2xl font-bold text-gray-900">✨ AI Insights</h3>
        <p className="text-sm text-gray-500 mt-1">Powered by OpenAI GPT-4o</p>
      </div>

      <button
        onClick={fetchInsights}
        disabled={loading}
        className="mb-6 px-6 py-3 bg-gradient-to-r from-blue-500 via-purple-500 to-pink-500 text-white rounded-xl font-bold hover:shadow-2xl disabled:opacity-50 transition-all transform hover:scale-105 active:scale-95"
      >
        {loading ? (
          <span className="flex items-center justify-center">
            <span className="animate-spin mr-2">⚙️</span>
            Generating...
          </span>
        ) : (
          "🚀 Generate Insights"
        )}
      </button>

      {error && (
        <div className="bg-red-50 border-2 border-red-200 text-red-700 p-4 rounded-xl mb-4 font-medium">
          ⚠️ Error: {error}
        </div>
      )}

      {insights && (
        <div className="bg-gradient-to-br from-blue-50 to-purple-50 p-6 rounded-xl border-2 border-blue-100 flex-1 overflow-auto">
          <div className="prose prose-sm max-w-none text-gray-800 text-sm leading-relaxed">
            {/* Format the insights with proper markdown-like rendering */}
            {insights.split("\n").map((line, idx) => {
              // Bold headings starting with ###
              if (line.startsWith("###")) {
                return (
                  <h4 key={idx} className="font-bold text-blue-900 mt-4 mb-2">
                    {line.replace(/^###\s*/, "")}
                  </h4>
                );
              }
              // Bold headings starting with ##
              if (line.startsWith("##")) {
                return (
                  <h3
                    key={idx}
                    className="font-bold text-blue-900 mt-4 mb-2 text-base"
                  >
                    {line.replace(/^##\s*/, "")}
                  </h3>
                );
              }
              // Skip empty lines but add spacing
              if (line.trim() === "") {
                return <div key={idx} className="h-2"></div>;
              }
              // Bullet points
              if (line.trim().startsWith("-")) {
                return (
                  <div key={idx} className="ml-4 mb-1">
                    • {line.replace(/^-\s*/, "")}
                  </div>
                );
              }
              // Regular paragraphs
              return (
                <p key={idx} className="mb-2">
                  {line}
                </p>
              );
            })}
          </div>
        </div>
      )}

      {!insights && !error && !loading && (
        <div className="text-center py-12 text-gray-400">
          <p className="text-4xl mb-2">🔮</p>
          <p className="font-medium">Click the button to generate insights</p>
        </div>
      )}
    </div>
  );
}
