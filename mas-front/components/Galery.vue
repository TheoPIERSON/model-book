<template>
  <section class="relative min-h-screen w-full bg-black flex justify-center flex-wrap content-start">
    <!-- Boucle pour afficher les images cliquables -->
    <NuxtImg
      alt="Picture of the model"
      width="200px"
      height="300px"
      v-for="(image, index) in images"
      :key="index"
      :src="image"
      class="image-thumb w-1/2 md:w-1/4 cursor-pointer p-2 object-cover h-auto"
      @click="openModal(image)"
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
    <div class="w-1/2 bg-red-500 absolute z-20"></div>
  </section>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from "vue";

const images = ["max-red.webp", "max-blue.webp", "max-green.webp", "max-purple.webp", "max-red2.webp"];
const isModalOpen = ref(false);
const modalImage = ref("");

const openModal = (imageSrc: string) => {
  modalImage.value = imageSrc;
  isModalOpen.value = true;
};

const closeModal = () => {
  isModalOpen.value = false;
};

// Fonction pour observer les images lorsqu'elles entrent dans le viewport
const observer = ref<IntersectionObserver | null>(null);

onMounted(() => {
  observer.value = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          entry.target.classList.add("visible");
        }
      });
    },
    { threshold: 0.1 } // Le seuil à partir duquel l'élément est considéré comme visible
  );

  const images = document.querySelectorAll(".image-thumb");
  images.forEach((image) => observer.value?.observe(image));
});

onBeforeUnmount(() => {
  if (observer.value) {
    observer.value.disconnect();
  }
});
</script>
<style scoped>
.image-thumb {
  opacity: 0;
  transition: opacity 0.6s ease-in-out;
}

.image-thumb.visible {
  opacity: 1;
}
</style>
