/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{vue,js,ts}"],
  theme: {
    extend: {
      borderRadius: {
        "btn-radius": "0.5rem",
      },
      colors: {
        "dodgeroll-gold": "#F79F1A",
        "apple-green": "#046E1B",
        "dire-wolf": "#292727",
      },
    },
    fontFamily: {
      Montserrat: "Montserrat, sans-serif",
      CourierPrime: ["'Courier Prime'", "monospace"], // Assure-toi que le nom de la police est bien celui de Google Fonts
      NewRocker: ["'New Rocker'", "monospace"],
    },
    container: {
      center: true,
      padding: "2rem",
    },
  },

  plugins: [
    function ({ addUtilities }) {
      addUtilities({
        ".mask-gradient": {
          "mask-image": "linear-gradient(to bottom, rgba(0, 0, 0, 1) 60%, rgba(0, 0, 0, 0) 100%)",
          "-webkit-mask-image": "linear-gradient(to bottom, rgba(0, 0, 0, 1) 66%, rgba(0, 0, 0, 0) 100%)",
        },
      });
    },
    function ({ addComponents }) {
      const buttons = {
        ".btn": {
          color: "#fff",
          padding: "0.5rem 0.2rem",
          borderBottom: "1px solid #059669",

          "&:hover": {
            borderBottom: "5px solid #059669",
            // emerald-600
          },
          "&:focus": {
            borderBottom: "4px solid #059669",
            outline: "none",
            ring: "2px",
            ringColor: "#10b981", // emerald-500
          },

          "&:active": {
            borderBottom: "4px solid #059669",
            outline: "none",
            ring: "2px",
            ringColor: "#10b981", // emerald-500
          },
        },
      };
      addComponents(buttons);
    },
  ],
};
