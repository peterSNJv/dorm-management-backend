<script setup>
import { ref } from 'vue'

const settings = ref({
  deepseek_key: localStorage.getItem('deepseek_key') || '',
  wechat_app_id: localStorage.getItem('wechat_app_id') || '',
  wechat_app_secret: localStorage.getItem('wechat_app_secret') || '',
  sd_url: localStorage.getItem('sd_url') || 'http://localhost:7860',
  pexels_key: localStorage.getItem('pexels_key') || '',
})

function save() {
  Object.entries(settings.value).forEach(([k, v]) => localStorage.setItem(k, v))
  alert('保存成功')
}
</script>

<template>
  <div class="max-w-xl space-y-4">
    <h2 class="text-lg font-semibold">设置</h2>
    <div class="bg-white rounded-lg shadow-sm p-6 space-y-4">
      <div>
        <label class="block text-sm font-medium text-gray-700 mb-1">DeepSeek API Key</label>
        <input v-model="settings.deepseek_key" type="password" class="w-full border rounded-lg px-3 py-2" placeholder="sk-..." />
      </div>
      <div>
        <label class="block text-sm font-medium text-gray-700 mb-1">微信公众号 AppID</label>
        <input v-model="settings.wechat_app_id" class="w-full border rounded-lg px-3 py-2" placeholder="wx..." />
      </div>
      <div>
        <label class="block text-sm font-medium text-gray-700 mb-1">微信公众号 AppSecret</label>
        <input v-model="settings.wechat_app_secret" type="password" class="w-full border rounded-lg px-3 py-2" />
      </div>
      <div>
        <label class="block text-sm font-medium text-gray-700 mb-1">Stable Diffusion 地址</label>
        <input v-model="settings.sd_url" class="w-full border rounded-lg px-3 py-2" placeholder="http://localhost:7860" />
      </div>
      <div>
        <label class="block text-sm font-medium text-gray-700 mb-1">Pexels API Key</label>
        <input v-model="settings.pexels_key" type="password" class="w-full border rounded-lg px-3 py-2" />
      </div>
      <button @click="save" class="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700">保存设置</button>
    </div>
  </div>
</template>
