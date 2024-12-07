<template>
  <form @submit.prevent="handleSubmit" class="grid place-items-center">
    <section class="grid md:flex w-full justify-around items-center mt-10">
      <div class="w-1/2 grid place-items-center">
        <label for="title" class="w-1/2">Titre de l'image</label>
        <input type="text" id="title" v-model="title" required class="border-b border-l w-1/2" />
      </div>
      <div class="w-1/2">
        <label for="photo">Ajouter une photo</label>
        <div
          class="file-drop-zone mx-10 h-52 flex items-center justify-center"
          @dragover.prevent
          @dragenter.prevent
          @drop.prevent="handleDrop"
        >
          <input type="file" id="photo" accept="image/*" @change="handleFileSelect" hidden ref="fileInput" />
          <p @click="triggerFileInput"><Icon name="material-symbols:photo-filter-sharp" class="w-full p-10" /></p>
          <p v-if="selectedFile">{{ selectedFile.name }}</p>
        </div>
      </div>
    </section>
    <button type="submit" class="m-10">Envoyer</button>
  </form>
</template>

<script setup lang="ts">
import { ref } from "vue";

const title = ref("");
const selectedFile = ref<File | null>(null);
const fileInput = ref<HTMLInputElement | null>(null);

const handleFileSelect = (event: Event) => {
  const target = event.target as HTMLInputElement;
  const file = target.files?.[0];
  if (file) {
    selectedFile.value = file;
  }
};

const handleDrop = (event: DragEvent) => {
  const file = event.dataTransfer?.files[0];
  if (file && file.type.startsWith("image/")) {
    selectedFile.value = file;
  }
};

const triggerFileInput = () => {
  fileInput.value?.click();
};

const handleSubmit = () => {
  if (!selectedFile.value) {
    alert("Veuillez sélectionner une image.");
    return;
  }
  console.log("Image:", selectedFile.value);
  console.log("Titre:", title.value);
  // Ajouter une logique pour envoyer les données au backend
};
</script>

<style scoped>
.file-drop-zone {
  border: 2px dashed #ccc;
  padding: 20px;
  text-align: center;
  cursor: pointer;
  transition: border-color 0.3s;
}
.file-drop-zone:hover {
  border-color: #999;
}
</style>
