<template>
  <section id="hero" class="h-screen">
    <NuxtImg
      alt="red background image"
      src="red-bg.webp"
      class="w-screen h-screen absolute -z-10"
      width="600px"
      height="500px"
    />
    <div class="flex justify-between md:w-screen h-screen fixed">
      <NuxtImg
        alt="Model looking to the right"
        src="file2.webp"
        class="hidden md:flex w-3/6 h-full object-cover"
        width="500px"
        height="600px"
      />
      <div class="w-20"></div>
      <NuxtImg
        alt="Model looking to the left"
        src="file.webp"
        class="md:w-4/6 h-full object-cover"
        width="500px"
        height="600px"
      />
    </div>
    <div class="h-screen"></div>
  </section>
</template>

<script setup lang="ts"></script>
