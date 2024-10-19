<template>
  <section id="hero">
    <NuxtImg src="red-bg.jpg" class="w-screen h-screen absolute -z-10" />
    <div class="flex justify-between md:w-screen h-screen fixed">
      <NuxtImg src="file2.png" class="hidden md:flex w-3/6 h-full object-cover" />
      <div class="w-20"></div>
      <NuxtImg src="file.png" class="md:w-4/6 h-full object-cover" />
    </div>
  </section>
</template>

<script setup lang="ts"></script>
