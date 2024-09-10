/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      colors: {
        primary: {
          DEFAULT: "#2563eb", // A professional blue tone
          foreground: "#ffffff", // White text for good contrast on blue
          90: "rgba(37, 99, 235, 0.9)", // Slightly transparent blue
        },
        secondary: {
          DEFAULT: "#64748b", // Neutral gray with a hint of blue
          foreground: "#ffffff",
          80: "rgba(100, 116, 139, 0.8)", // 80% opacity variant
        },
        destructive: {
          DEFAULT: "#ef4444", // Red for actions like delete, unsubscribe, etc.
          foreground: "#ffffff",
          90: "rgba(239, 68, 68, 0.9)",
        },
        accent: {
          DEFAULT: "#10b981", // A soothing green for success actions and highlights
          foreground: "#ffffff",
        },
        background: "#f9fafb", // Very light gray almost white, for backgrounds
        input: "#d1d5db", // Lighter gray for input fields and borders
      },
    },
  },
  plugins: [],
};
