/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {
      colors: {
        surface: {
          50: '#ffffff',
          100: '#f5f5f5',
          200: '#e5e5e5',
          900: '#1a1a1a',
          950: '#000000',
        }
      },
      borderWidth: {
        'brutal': '2px',
      },
      boxShadow: {
        'brutal': '4px 4px 0px 0px rgba(0, 0, 0, 1)',
        'brutal-hover': '2px 2px 0px 0px rgba(0, 0, 0, 1)',
      }
    },
  },
  plugins: [],
}
