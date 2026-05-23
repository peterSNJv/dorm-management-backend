<script setup>
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()

const navItems = [
  { path: '/create', label: '创作' },
  { path: '/history', label: '历史' },
  { path: '/settings', label: '设置' },
]
</script>

<template>
  <div class="min-h-screen bg-gray-50">
    <nav class="bg-white shadow-sm border-b">
      <div class="max-w-6xl mx-auto px-4 flex items-center h-14">
        <h1 class="text-lg font-bold text-gray-800 mr-8">公众号 Agent</h1>
        <button
          v-for="item in navItems"
          :key="item.path"
          @click="router.push(item.path)"
          :class="[
            'px-4 py-2 rounded-lg text-sm font-medium transition-colors',
            route.path === item.path
              ? 'bg-blue-50 text-blue-700'
              : 'text-gray-600 hover:bg-gray-100',
          ]"
        >
          {{ item.label }}
        </button>
      </div>
    </nav>
    <main class="max-w-6xl mx-auto px-4 py-6">
      <RouterView />
    </main>
  </div>
</template>
