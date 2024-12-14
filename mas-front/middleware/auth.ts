import { useCookie, navigateTo } from "#app";

export default defineNuxtRouteMiddleware(() => {
  const authToken = useCookie("auth_token").value;
  if (!authToken) {
    return navigateTo("/login");
  }
});
