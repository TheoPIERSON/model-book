// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  compatibilityDate: "2024-04-03",
  app: {
    head: {
      title: "MAS Professional Model",
      meta: [
        {
          name: "description",
          content: "Professional model based in Paris - France book, you can see here his portfolio and his contacts",
        }, // Balise meta de description
      ],
      link: [
        { rel: "icon", type: "image/x-icon", href: "icon.png" }, // Chemin correct pour le favicon dans static/
        // Tu peux ajouter d'autres tailles d'icônes si nécessaire
        { rel: "apple-touch-icon", sizes: "180x180", href: "/images/apple-touch-icon.png" }, // Exemple d'icône pour mobile
      ],
    },
  },
  devtools: { enabled: true },
  modules: ["@nuxt/image", "@nuxt/fonts", "@nuxtjs/tailwindcss", "@nuxt/icon"],
  runtimeConfig: {
    public: {
      apiBaseUrl: process.env.API_BASE_URL,
    },
  },
});
