<script setup>
defineProps({ images: Array, loading: Boolean })
const emit = defineEmits(['select'])
</script>

<template>
  <div>
    <div v-if="loading" class="text-gray-400 text-sm">搜索中...</div>
    <div v-else-if="images && images.length" class="grid grid-cols-5 gap-2">
      <div
        v-for="(img, i) in images"
        :key="i"
        @click="emit('select', img)"
        class="aspect-video rounded-lg overflow-hidden cursor-pointer border-2 border-transparent hover:border-blue-500 transition-colors"
      >
        <img :src="img.thumbnail" class="w-full h-full object-cover" :alt="img.photographer" />
      </div>
    </div>
    <div v-else class="text-gray-400 text-sm">点击"搜索配图"查找图片</div>
  </div>
</template>
