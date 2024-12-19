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
      :style="`--index: ${index}`"
      class="image-item w-1/2 md:w-1/4 cursor-pointer p-2 object-cover h-auto"
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
import { onMounted, ref, nextTick } from "vue";
import { useNuxtApp } from "#app";
import { gsap } from "gsap";

const images = ref<string[]>([]);
const isModalOpen = ref(false);
const modalImage = ref<string>("");

const { $axios } = useNuxtApp();
const backendBaseUrl = "http://localhost:8080";

const fetchImages = async () => {
  try {
    const response = await $axios.get("/photos/list");
    images.value = response.data;
    console.log("Images récupérées :", images.value);

    // Attendre le prochain cycle de rendu pour que les images soient présentes dans le DOM
    await nextTick();
    gsap.from(".image-item", {
      opacity: 0,
      y: 20,
      duration: 1,
      stagger: 0.2, // Délai entre chaque animation
    });
  } catch (error) {
    console.error("Erreur lors de la récupération des images :", error);
  }
};

const getImageUrl = (fileName: string) => `${backendBaseUrl}/uploads/${fileName}`;

const openModal = (imageSrc: string) => {
  modalImage.value = imageSrc;
  isModalOpen.value = true;
};

const closeModal = () => {
  isModalOpen.value = false;
};

onMounted(() => {
  fetchImages();
});
</script>

<style scoped>
.image-item {
  opacity: 0;
  transform: translateY(20px);
  animation: fadeIn 1s ease-out forwards;
  animation-delay: calc(var(--index) * 0.2s); /* Délai pour chaque image */
}

@keyframes fadeIn {
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
