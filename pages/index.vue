<template>
  <BaseNavigation />
  <main>
    <HeroBanner id="hero" class="h-screen" />
    <Portfolio id="portfolio" />
    <Contact id="contact" />
    <!-- Ajoute tes autres sections ici -->
  </main>
</template>

<script setup lang="ts">
import { onMounted, onUnmounted } from "vue";
import { gsap } from "gsap";
import { ScrollTrigger, ScrollToPlugin } from "gsap/all";

// Enregistre les plugins
gsap.registerPlugin(ScrollTrigger, ScrollToPlugin);

onMounted(() => {
  const sections = document.querySelectorAll("section");

  const scrolling = {
    enabled: true,
    events: "scroll,wheel,touchmove,pointermove".split(","),
    prevent: (e: Event) => e.preventDefault(), // On spécifie que 'e' est un événement
    disable() {
      if (scrolling.enabled) {
        scrolling.enabled = false;
        window.addEventListener("scroll", gsap.ticker.tick, { passive: true });
        scrolling.events.forEach((e) => window.addEventListener(e, scrolling.prevent, { passive: false }));
      }
    },
    enable() {
      if (!scrolling.enabled) {
        scrolling.enabled = true;
        window.removeEventListener("scroll", gsap.ticker.tick);
        scrolling.events.forEach((e) => window.removeEventListener(e, scrolling.prevent));
      }
    },
  };

  function goToSection(section: HTMLElement, anim?: gsap.core.Animation) {
    if (scrolling.enabled) {
      scrolling.disable();
      gsap.to(window, {
        scrollTo: { y: section, autoKill: false },
        onComplete: scrolling.enable,
        duration: 1,
      });
      anim && anim.restart();
    }
  }

  sections.forEach((section) => {
    const intoAnim = gsap.from(section.querySelector(".right-col"), {
      yPercent: 50,
      duration: 1,
      paused: true,
    });

    ScrollTrigger.create({
      trigger: section,
      start: "top bottom-=1",
      end: "bottom top+=1",
      onEnter: () => goToSection(section, intoAnim),
      onEnterBack: () => goToSection(section),
    });
  });
});

onUnmounted(() => {
  ScrollTrigger.getAll().forEach((trigger) => trigger.kill());
});
</script>

<style scoped>
/* Style pour ajuster les sections */
section {
  height: auto;
  position: relative;
}
</style>
