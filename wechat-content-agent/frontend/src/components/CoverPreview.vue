<script setup>
defineProps({ url: String, loading: Boolean })
</script>

<template>
  <div class="border rounded-lg overflow-hidden bg-gray-100 flex items-center justify-center min-h-[200px]">
    <div v-if="loading" class="text-gray-400 text-sm">生成中...</div>
    <img v-else-if="url" :src="url" class="w-full object-cover" alt="封面图" />
    <div v-else class="text-gray-400 text-sm">等待生成封面</div>
  </div>
</template>
