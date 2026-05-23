<script setup>
import { onMounted } from 'vue'
import { useContentStore } from '../stores/content'

const store = useContentStore()

onMounted(() => store.fetchHistory())
</script>

<template>
  <div class="space-y-4">
    <h2 class="text-lg font-semibold">历史文章</h2>
    <div v-if="!store.history.length" class="text-gray-400 text-center py-12">暂无文章</div>
    <div v-for="item in store.history" :key="item.id" class="bg-white rounded-lg shadow-sm p-4 flex justify-between items-center">
      <div>
        <h3 class="font-medium">{{ item.title }}</h3>
        <p class="text-sm text-gray-500 mt-1">{{ item.summary }}</p>
      </div>
      <span :class="['text-xs px-2 py-1 rounded', item.status === 'published' ? 'bg-green-100 text-green-700' : 'bg-yellow-100 text-yellow-700']">
        {{ item.status === 'published' ? '已发布' : '草稿' }}
      </span>
    </div>
  </div>
</template>
