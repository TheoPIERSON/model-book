<template>
  <section class="relative min-h-screen w-full bg-black flex justify-center flex-wrap">
    <!-- Boucle pour afficher les images cliquables -->
    <NuxtImg
      v-for="(image, index) in images"
      :key="index"
      :src="image"
      class="image-thumb w-1/4 cursor-pointer p-2 object-cover"
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
          <NuxtImg :src="modalImage" class="modal-image" />
        </div>
      </div>
    </transition>
    <div class="w-1/2 bg-red-500 absolute z-20"></div>
  </section>
</template>

<script setup lang="ts">
import { ref } from "vue";

// Créer un tableau d'images
const images = ["max-red.png", "max-blue.png", "max-green.png", "max-purple.png", "max-red2.png"];

// Créer une variable pour gérer l'état de la modale et l'image sélectionnée
const isModalOpen = ref(false);
const modalImage = ref("");

// Fonction pour ouvrir la modale avec l'image cliquée
const openModal = (imageSrc: string) => {
  modalImage.value = imageSrc;
  isModalOpen.value = true;
};

// Fonction pour fermer la modale
const closeModal = () => {
  isModalOpen.value = false;
};
</script>
