import axios from "axios";

export default defineNuxtPlugin(() => {
  const instance = axios.create({
    baseURL: "http://localhost:8080", // URL de votre backend Spring Boot
  });

  return {
    provide: {
      axios: instance,
    },
  };
});
