export const login = async (email: string, password: string) => {
  const config = useRuntimeConfig();
  try {
    const response = await $fetch("/user/connexion", {
      baseURL: config.public.apiBaseUrl,
      method: "POST",
      body: { email, password },
    });
    return response;
  } catch (error) {
    throw error;
  }
};
