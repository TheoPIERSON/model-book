import axios from "axios";

export default defineNuxtPlugin(() => {
  const instance = axios.create({
    baseURL: "http://localhost:8080",
  });

  const noAuthInstance = axios.create({
    baseURL: "http://localhost:8080",
    headers: {
      Authorization: "", // Ne pas inclure de JWT
    },
  });

  return {
    provide: {
      axios: instance,
      axiosNoAuth: noAuthInstance,
    },
  };
});
