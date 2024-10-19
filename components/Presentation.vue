<template>
  <section class="p-10 md:p-16">
    <p ref="text1">Name : Mas</p>
    <p ref="text2">Location : Paris</p>
    <p ref="text3">Height : 185</p>
    <p ref="text4">Chest : 82</p>
    <p ref="text5">Waist : 75</p>
    <p ref="text6">Shoe : 10</p>
    <p ref="text7">Eyes : Brown</p>
    <p ref="text8">Hair : Black</p>
  </section>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";
import { gsap } from "gsap";
import { TextPlugin } from "gsap/TextPlugin";

// Active le plugin TextPlugin de GSAP
gsap.registerPlugin(TextPlugin);

// Références pour chaque paragraphe
const text1 = ref<HTMLElement | null>(null);
const text2 = ref<HTMLElement | null>(null);
const text3 = ref<HTMLElement | null>(null);
const text4 = ref<HTMLElement | null>(null);
const text5 = ref<HTMLElement | null>(null);
const text6 = ref<HTMLElement | null>(null);
const text7 = ref<HTMLElement | null>(null);
const text8 = ref<HTMLElement | null>(null);

onMounted(() => {
  const elements = [text1, text2, text3, text4, text5, text6, text7, text8];

  const startAnimation = (element: HTMLElement, index: number) => {
    gsap.fromTo(
      element,
      { text: "." }, // Commence avec un texte vide
      {
        text: element.textContent || "", // Si textContent est null, utiliser une chaîne vide
        duration: 1.3, // Durée de l'animation
        delay: index * 1.3, // Décalage entre chaque ligne
        ease: "none", // Pas d'effet d'accélération
      }
    );
  };

  // Configuration de l'Intersection Observer
  const observer = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          const targetIndex = elements.findIndex((ref) => ref.value === entry.target);
          if (targetIndex !== -1) {
            startAnimation(entry.target as HTMLElement, targetIndex);
            observer.unobserve(entry.target); // Arrête de surveiller après l'animation
          }
        }
      });
    },
    { threshold: 0.2 } // Démarre l'animation quand 10% de l'élément est visible
  );

  // Observer chaque élément
  elements.forEach((textRef) => {
    if (textRef.value) {
      observer.observe(textRef.value);
    }
  });
});
</script>

<style scoped></style>
