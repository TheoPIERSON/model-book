<template>
  <section class="relative min-h-screen w-full bg-black flex justify-center flex-wrap content-start">
    <!-- Boucle pour afficher les images cliquables -->
    <NuxtImg
      v-for="(image, index) in images"
      :key="index"
      :src="getImageUrl(image)"
      alt="Picture of the model"
      width="200px"
      height="300px"
      class="w-1/2 md:w-1/4 cursor-pointer p-2 object-cover h-auto"
      @click="openModal(getImageUrl(image))"
    />
    <!-- Modale avec transition pour l'animation -->
    <transition name="scale">
      <div
        v-if="isModalOpen"
        class="fixed inset-0 bg-black bg-opacity-75 flex items-center justify-center z-50"
        @click.self="closeModal"
      >
        <div class="relative">
          <!-- Image agrandie dans la modale -->
          <NuxtImg :src="modalImage" class="modal-image p-2" alt="Picture of the model in full screen" />
        </div>
      </div>
    </transition>
  </section>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useNuxtApp } from "#app";

const { $axios, $axiosNoAuth } = useNuxtApp(); // Utilisation d'Axios via le plugin Nuxt

const images = ref<string[]>([]); // Stocke les noms des fichiers récupérés
const isModalOpen = ref(false);
const modalImage = ref<string>("");

// URL de base pour accéder aux images
const backendBaseUrl = "http://localhost:8080";

// Récupère la liste des fichiers depuis le backend
const fetchImages = async () => {
  try {
    const response = await $axios.get("/photos/list");
    images.value = response.data;
    console.log("Images récupérées :", images.value);
    images.value.forEach((image) => console.log("URL complète :", getImageUrl(image)));
  } catch (error) {
    console.error("Erreur lors de la récupération des images :", error);
  }
};

// Génère l'URL complète d'une image
const getImageUrl = (fileName: string) => `${backendBaseUrl}/uploads/${fileName}`;

const openModal = (imageSrc: string) => {
  modalImage.value = imageSrc;
  isModalOpen.value = true;
};

const closeModal = () => {
  isModalOpen.value = false;
};
const testImageRequest = async (fileName: string) => {
  try {
    const response = await $axiosNoAuth.get(getImageUrl(fileName), {
      responseType: "blob",
    });
    console.log("Image chargée avec succès :", response);
  } catch (error) {
    console.error("Erreur lors du chargement de l'image :", error);
  }
};

// Charge les images au montage du composant
onMounted(() => {
  fetchImages();
  console.log(getImageUrl("max-green.webp"));
});
</script>

<style scoped></style>
